package com.adwork.adworktest.db.services.impl;

import com.adwork.adworktest.db.entities.Response;
import com.adwork.adworktest.db.entities.User;
import com.adwork.adworktest.db.repository.IUserRepo;
import com.adwork.adworktest.db.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public Response saveOrUpdateUser(User user) {
        Response response = new Response();

        if (user.getIdentificacion() == null || user.getIdentificacion().length() == 0 || user.getIdentificacion().length() > 50) {
            response.setCod("0");
            response.setMessage("la identificación es obligatoria y no debe exceder los 50 caracteres");
            response.setResult(false);
            return response;
        }

        if (user.getNombres() == null || user.getNombres().length() == 0 || user.getNombres().length() > 100) {
            response.setCod("0");
            response.setMessage("El nombre es obligatorío y no debe exceder de los 100 caracteres");
            response.setResult(false);
            return response;
        }

        if (user.getApellidos() == null || user.getApellidos().length() == 0 || user.getApellidos().length() > 100) {
            response.setCod("0");
            response.setMessage("El apellido es obligatorío y no debe exceder de los 100 caracteres");
            response.setResult(false);
            return response;
        }

        if (user.getEdad() == null || user.getEdad() < 18) {
            response.setCod("0");
            response.setMessage("La edad es obligatorío y no debe ser mínimo de 18 años");
            response.setResult(false);
            return response;
        }

        if (user.getGenero() == null || user.getGenero().length() != 1) {
            response.setCod("0");
            response.setMessage("El género es obligatorío y su valor se encuentra entre M para masculino o F para femenino");
            response.setResult(false);
            return response;
        } else {
            boolean valid;
            if ("F".equals(user.getGenero().toUpperCase()))
                valid = true;
            else valid = "M".equals(user.getGenero().toUpperCase());

            if (!valid) {
                response.setCod("0");
                response.setMessage("El valor del género se encuentra entre M para masculino o F para femenino");
                response.setResult(false);
                return response;
            }
        }

        if (user.getCiudad() == null || user.getCiudad().length() == 0 || user.getCiudad().length() > 50) {
            response.setCod("0");
            response.setMessage("La ciudad es obligatoría y no debe exceder de los 50 caracteres");
            response.setResult(false);
            return response;
        }

        if (user.getUsuario() == null || user.getUsuario().length() == 0 || user.getUsuario().length() > 50) {
            response.setCod("0");
            response.setMessage("El usuario es obligatorío y no debe exceder de los 50 caracteres");
            response.setResult(false);
            return response;
        } else {
            String nombres = user.getNombres().trim(), apellidos = user.getApellidos().trim();
            String[] partsN = nombres.split(" ");
            String[] partsA = apellidos.split(" ");
            if (partsN.length > 0) {
                boolean ret = false;
                for (String part : partsN) {
                    if (part.length() >= 1 && user.getUsuario().trim().toUpperCase().contains(part.toUpperCase())) {
                        ret = true;
                    }
                }
                if (ret) {
                    response.setCod("0");
                    response.setMessage("El usuario no debe contener los nombres o los apellidos o la combinación de éstos");
                    response.setResult(false);
                    return response;
                }
            }

            if (partsA.length > 0) {
                boolean ret = false;
                for (String part : partsA) {
                    if (part.length() >= 1 && user.getUsuario().trim().toUpperCase().contains(part.toUpperCase())) {
                        ret = true;
                    }
                }
                if (ret) {
                    response.setCod("0");
                    response.setMessage("El usuario no debe contener los nombres o los apellidos o la combinación de éstos");
                    response.setResult(false);
                    return response;
                }
            }

        }

        if (user.getPassword() == null || user.getPassword().length() == 0 || user.getPassword().length() > 50) {
            response.setCod("0");
            response.setMessage("El password es obligatorío y no debe exceder de los 50 caracteres");
            response.setResult(false);
            return response;
        } else {
            String fecha = user.getFecha_nacimiento().trim();
            String[] partsF = fecha.split("/");
            if (partsF.length > 0) {
                boolean ret = false;
                for (String part : partsF) {
                    if (user.getPassword().trim().toUpperCase().contains(part.toUpperCase()))
                        ret = true;
                }
                if (ret) {
                    response.setCod("0");
                    response.setMessage("El password no debe contener el mes o el dia o el año de la fecha de nacimiento");
                    response.setResult(false);
                    return response;
                }
            }
        }

        if (user.getFecha_nacimiento() == null || user.getFecha_nacimiento().length() == 0 || user.getFecha_nacimiento().length() > 50) {
            response.setCod("0");
            response.setMessage("La fecha de nacimiento es obligatoría y no debe exceder de los 50 caracteres");
            response.setResult(false);
            return response;
        } else {
            boolean valid;
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            sdf.setLenient(false);
            try {
                date = sdf.parse(user.getFecha_nacimiento().trim());
                valid = true;
            } catch (ParseException e) {
                valid = false;
            }
            if (!valid) {
                response.setCod("0");
                response.setMessage("El formato para la fecha de nacimiento es MM/dd/yyyy");
                response.setResult(false);
                return response;
            } /*else {
                LocalDate today = LocalDate.now();

                Instant instant = Instant.ofEpochMilli(date.getTime());
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                LocalDate birthday = localDateTime.toLocalDate();
                Period period = Period.between(birthday, today);
                if (user.getEdad() == period.getYears()){
                    response.setCod("0");
                    response.setMessage("El formato para la fecha de nacimiento es MM/dd/yyyy");
                    response.setResult(false);
                    return response;
                }

            }*/
        }

        User userValid = userRepo.findByIdentificacion(user.getIdentificacion());
        if (userValid != null && userValid.getId() != null) {
            user.setId(userValid.getId());
            response.setMessage("Usuario actualizado correctamente");
        } else
            response.setMessage("Usuario registrado correctamente");
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        User userReturn = userRepo.save(user);
        response.setCod(userReturn.getId().toString());
        response.setResult(true);
        return response;
    }

    @Override
    public User findByIdentification(String identificacion) {
        return userRepo.findByIdentificacion(identificacion);
    }

}
