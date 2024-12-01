


# PinApp Challenge

---

**Tecnologías utilizadas**
* JDK 21
* Spring
* H2
* Lombok
* MapStruct
* JUnit + Mockito
* Docker



Host GCP: https://pinappchallenge-643178392609.us-central1.run.app  
Host Render: https://pinappchallenge.onrender.com

Swagger-ui: [host]/swagger-ui/index.html#/client-controller



## Endpoints

---


__Consulta de clientes__

__GET__ [host]/v1/clients?page=0&size=10

__URL Params__
```
    Parameters [
        page: 0,
        size: 10
    ]
```

__Respuesta de ejemplo__

```
    {
      "content": [
        {
          "id": 1,
          "name": "Sergio",
          "lastname": "Ramírez",
          "age": 25,
          "birthdate": "1999-09-10",
          "estimatedDateOfDeath": "2069-09-10"
        },
        {
          "id": 2,
          "name": "Darío",
          "lastname": "Ramírez",
          "age": 27,
          "birthdate": "1997-08-22",
          "estimatedDateOfDeath": "2067-08-22"
        }
      ],
      "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
          "sorted": false,
          "empty": true,
          "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
      },
      "totalElements": 2,
      "totalPages": 1,
      "last": true,
      "size": 10,
      "number": 0,
      "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
      },
      "numberOfElements": 2,
      "first": true,
      "empty": false
    }

```
---

__Consulta KPI de clientes__

Tanto para el promedio de edad como para el KPI se utiliza una **memoria cache** interna en el mismo servicio. Está modelada como un Map con política de actualización mediante una tarea croneada con @Schedule.  
Por fines prácticos la memoria cache se refresca cada **1 minuto**.


__GET__ [host]/v1/clients/kpi

__Respues de ejemplo__

```
    {
      "ageAverage": 28.4,
      "standardDeviation": 3.5552777669262494
    }

```

---

__Creación de clientes__


__POST__ [host]/v1/clients

__Body JSON__
```
    {
        "name": "Sergio",
        "lastname": "Ramírez",
        "age": 25,
        "birthdate": "1999-11-30T00:00:00.00"
    }
```

__Respues de ejemplo__

```
    {
      "ageAverage": 28.4,
      "standardDeviation": 3.5552777669262494
    }

```

---

## Datos H2

Tabla `TB_CLIENT`


<table>
  <tr>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">ID_CLIENT  </a>
   </td>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">NAME  </a>
   </td>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">LASTNAME  </a>
   </td>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">AGE  </a>
   </td>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">BIRTHDATE  </a>
   </td>
  </tr>
  <tr>
   <td>1
   </td>
   <td>Sergio
   </td>
   <td>Ramírez
   </td>    
<td>25
   </td>
<td>1999-09-11
   </td>
  </tr>
  <tr>
   <td>2
   </td>
   <td>Darío
   </td>
   <td>Ramírez
   </td>    
<td>27
   </td>
<td>1997-08-23
   </td>
  </tr>
<tr>
   <td>3
   </td>
   <td>Laura
   </td>
   <td>Ramírez
   </td>    
<td>29
   </td>
<td>1995-01-17
   </td>
  </tr>
<tr>
   <td>4
   </td>
   <td>Tomás
   </td>
   <td>Campos
   </td>    
<td>26
   </td>
<td>1998-09-11
   </td>
  </tr>
<tr>
   <td>5
   </td>
   <td>Eliana
   </td>
   <td>Ramírez
   </td>    
<td>35
   </td>
<td>1989-09-11
   </td>
  </tr>
</table>


Tabla `TB_APPPARAMETERS`

En esta tabla se almacena los parámetros de la aplicación. En este caso se almacena la esperanza de vida a nivel global.

<table>
  <tr>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">ID_APPPARAMETER  </a>
   </td>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">NAME  </a>
   </td>
   <td><a href="http://localhost:8080/h2-console/query.do?jsessionid=6b1155161c88e03a52366b62fdc932a4#">VALUE_PARAM  </a>
   </td>
  </tr>
  <tr>
   <td>1
   </td>
   <td>LONGEVIDAD_GLOBAL
   </td>
   <td>70
   </td>
  </tr>
</table>
