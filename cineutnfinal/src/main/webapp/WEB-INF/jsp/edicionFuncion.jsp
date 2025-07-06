<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Detalles y Edicion de Funcion de Cine</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center">Detalles de la Funcion ID : ${funcion.id_funcion}</h1>

            <!-- Informacion de la funcion -->
            <div class="card p-4">

                <form id="formEditarFuncion" action="updateFuncion" method="post">

                    <!-- Campo Pelicula -->
                    <div class="mb-3 row">
                        <label for="pelicula" class="col-sm-2 col-form-label">Pelicula:</label>
                        <div class="col-sm-10">
                             <!-- <input type="text" id="pelicula" name="pelicula" class="form-control" value="${funcion.pelicula.nombre_pelicula}" readonly> -->
                            <select id="pelicula" name="pelicula" class="form-control" required>
                                <!-- Mostrar la pelocula de la funcion como la opcion seleccionada por defecto -->
                                <option value="${funcion.pelicula.nombre_pelicula}" selected>${funcion.pelicula.nombre_pelicula}</option>

                                <!-- Lista de otras opciones de peloculas -->
                                <c:forEach items="${listaPeliculas}" var="pelicula">
                                    <option value="${pelicula.nombre_pelicula}">${pelicula.nombre_pelicula}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Campo Sala -->
                        <div class="mb-3 row">
                            <label for="sala" class="col-sm-2 col-form-label">Sala ID:</label>
                            <div class="col-sm-10">
                                <input type="text" id="salaId" name="salaId" class="form-control" value="${funcion.sala.id}" readonly>
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label for="sala" class="col-sm-2 col-form-label">Tipo de Sala:</label>
                            <div class="col-sm-10">
                                <input type="text" id="salaTipo" name="salaTipo" class="form-control" value="${funcion.sala.tipoDeSala}" readonly>
                            </div>
                        </div>

                        <!-- Campo Fecha de Funcion -->
                        <div class="mb-3 row">
                            <label for="fechaDeFuncion" class="col-sm-2 col-form-label">Fecha de Funcion </label>
                            <div class="col-sm-10">
                                <!-- preguntar por que usando type="date" no me levanta las fecha pasada !!!!!!-->
                                <input type="text" id="fechaDeFuncion" name="fechaDeFuncion" class="form-control" value="${funcion.fechaDeFuncion}" readonly> 
                            </div>
                        </div>

                        <!-- Campo Horario  -->
                        <div class="mb-3 row">
                            <label for="horario" class="col-sm-2 col-form-label">Horario:</label>
                            <div class="col-sm-10">
                                <input type="text" id="horario" name="horario" class="form-control" value="${funcion.horarioFuncion}" readonly> 
                            </div>
                            </div>
                        </div>

                        <!-- Botones de accion -->
                        <input type="hidden" name="idFuncion" value="${funcion.id_funcion}" />
                        <div class="mb-3 row">
                            <div class="col-sm-12 text-center">
                                <button type="submit" class="btn btn-success me-2">Confirmar cambios</button>
                                <button type="reset" class="btn btn-secondary">Reiniciar</button>
                            </div>
                        </div>
                </form>
            </div>


            <!-- Boton de regresar -->
            <div class="d-flex justify-content-center mt-4">
                <a href="javascript:history.back()" class="btn btn-primary">Regresar</a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
