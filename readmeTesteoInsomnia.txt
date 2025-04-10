TEST INSOMNIA ADMIN
---------------
POST REGISTER, BIEN
GET /BOOKS, BIEN
GET /MUSICALBUMS, BIEN
POST /CARTS, BIEN, 403
POST /BOOKS, BIEN, persiste el objeto
{
  "title": "El Principito",
  "author": "Antoine",
  "editorial": "Planeta",
  "description": "Un clásico",
  "isbn": "1234567890",
  "genreBooks": "ART",
  "price": 29.99,
  "stock": 10,
  "urlImage": ["https://link-a-la-imagen.com/portada.jpg"]
}
POST /BOOKS/BATCH, BIEN
[
  {
    "title": "Libro 1",
    "author": "Autor A",
    "editorial": "Editorial X",
    "description": "Desc 1",
    "isbn": "1111",
    "genreBooks": "TRAGICOMEDY",
    "price": 19.99,
    "stock": 5,
    "urlImage": ["img1.jpg"]
  },
  {
    "title": "Libro 2",
    "author": "Autor B",
    "editorial": "Editorial Y",
    "description": "Desc 2",
    "isbn": "2222",
    "genreBooks": "YOUNG",
    "price": 25.99,
    "stock": 3,
    "urlImage": ["img2.jpg"]
  }
]
PUT books/id, BIEN
{
  "stock": 15
}
POST musicAlbums
{
  "title": "Thriller",
  "author": "Michael Jackson",
  "recordLabel": "Epic",
  "year": 1982,
  "description": "Mejor álbum",
  "isrc": "USSM19999999",
  "genres": "POP",
  "price": 49.99,
  "stock": 20,
  "urlImage": ["https://imagen.com/thriller.jpg"]
}
ERROR, problema al manejar un array de URLs
2025-04-10T06:58:25.965-03:00  WARN 23972 --- [nio-8080-exec-5] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.util.ArrayList<com.uade.tpo.marketplace.entity.Genre>` from String value (token `JsonToken.VALUE_STRING`)]
TEST INSOMNIA BUYER
---------------------------------
POST auth/register
{
  "firstname": "Paula",
  "lastname": "Pérez",
  "email": "paulap@mail.com",
  "password": "1234",
  "role": "BUYER"
}
RESULTADO: ERROR AL UTILIZAR EL MISMO APELLIDO; CAMBIE QUE NO FUERA ALGO UNIQUE PERO SIGUE SIN FUNCIONAR,AL MISMO TIEMPO HICE QUE EL MAIL FUESE UNIQUE
	   Error al utilizar el mismo apelido; cambie que no fuera algo unique desde la clase user, al mismo tiemo agregue que no pueda ser null el mail y que fuera unique,
	   De todas formas, estuve horas buscando el error y no lo pude encontrar, por qué no puede haber apellidos repetidos, y al tratr de ponerle unique al mail
	   sigue permitiendo registrarse con el mismo mail. Desconozco cual es el error.
	   Posteriormente habría que cambiar cómo funciona la asignación de roles y que no sea algo de libre acceso(Ahora mismo sirve que esté así como esta)


Post auth/authorizate(O algo así)
  {
  "email": "juan@mail.com",
  "password": "1234"
}
Todo bien, no usamos paula perez por el error del apellido, de todas formas cree otros usuarios.

GET /Books
todo bien

POST /books y /books/batch 
Todo bien, tira 403

		
Post  /books/batch
Todo bien, 403

GET /books/search?tittle=algo
todo bien, muestra el contenido

GET /musicAlbums
todo bien, muestra todo perfecto 



PUT /musicAlbums
{
  "stock": 8
}
todo bien, tira 403

POST /musicAlbums
{
  "title": "Thriller",
  "author": "Michael Jackson",
  "recordLabel": "Epic",
  "year": 1982,
  "description": "Mejor álbum",
  "isrc": "USSM19999999",
  "genres": "POP",
  "price": 49.99,
  "stock": 20,
  "urlImage": ["https://imagen.com/thriller.jpg"]
}
todo bien, tira 403
GET /carts/userid
todo bien, muestra contenido
POST /carts
{
  "user": {
    "id": 1
  }
}
POST carts/1/books, lo mismo
ce.entity.Cart]]: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Multiple back-reference properties with name 'defaultReference'
2025-04-10T06:38:42.043-03:00  WARN 23972 --- [nio-8080-exec-2] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/json;charset=UTF-8' is not supported]
Salta este error, intente forzar que consumiera, trate de restructurar cart, cartitem y hasta product, pero sigue saltando error una y otra vez
No encuentro una solución clara.
Creo que fue un conflicto cuando cambiamos como funcionaba product, libro y album, pero no quiero meter mano ahí, también pienso que puede ser por eso de Jackson, lo
saque pero persiste el problema.
POST /Purchasedocuments lo mismo
2025-04-10T07:02:05.876-03:00  WARN 23972 --- [nio-8080-exec-2] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/json;charset=UTF-8' is not supported]



