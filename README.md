# Movieflix App
Aplicación Android de peliculas realizada como muesta de conceptos utilizando The Movie Database API.

## Instalación
* Android Studio 4.1

## Features
Esta app contiene 3 pantallas:

![alt text](https://github.com/nicoduarte/movieflix/blob/main/art/listado.png)
![alt text](https://github.com/nicoduarte/movieflix/blob/main/art/detalle.png)
![alt text](https://github.com/nicoduarte/movieflix/blob/main/art/busqueda.png)

* Lista de peliculas
* Detalle de pelicula
* Búsqueda de peliculas

## Arquitectura utilizada
Se empleó la arquitectura MVVM (Model-View-ViewModel) como capa de presentación.

Activity / Fragment: Es el encargado de mostrar datos y enviar enventos de UI. La vista y el viewModel se comunican usando LiveData.
LiveData es un observable que notifica a la UI por actualizaciones. Es tambien consciente del ciclo de vida y esto evita crashes parando las actualizaciones

Las clases son: 
* MainActivity
* MovieDetailActivity
* SearchActivity
    
    
ViewModel: Prepara los datos para su visualizacion en la UI y reacciona a las interacciones del usuario.
La vista se subscribe al correspondiente LiveData.

Las clases son: 
* MainViewModel
* DetailViewModel 
* SearchViewModel
  
Repository: Es el encargado de obtener los datos ya sea localmente o alojado en la nube.
* Clase: MovieRepository
  
Database: Almacena los datos localmente. Fuente de datos SQLite usando objetos. Se utilizó Room y RxJava
* Clase: MovieDatabase
  
Remote data: Comunicación con backend usando Retrofit y RxJava
* Clase: ApiService

## Testing
El proyecto usa test instrumentacion que corren en el dispositivo.

* UI Tests: Se empleó Espresso.
* Database Tests: Se crea una base de datos en memoria
