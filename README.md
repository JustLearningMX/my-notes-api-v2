[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

# About the project 📝

This project is an API REST developed in Spring Boot that provides a Notes App.
It allows users to create, edit and delete <b>notes</b>, as well as manage their categories. 
The application uses a MySQL database for data storage and is hosted on the Heroku platform.

<b>Main features: </b>📌

<ul>
    <li>Note creation and administration.</li>
    <li>Secure connection to the database in the cloud.</li>
    <li>Deployment and execution on Heroku.</li>
</ul>

## Built With 🔐

The stack use to develop and deploy this project is:

* Spring Boot 3.2
* Maven 3.8
* Spring Web
* Spring Data JPA
* Spring security
* Authentication with JWT (auth0)
* MySql Driver
* MySQL 8+
* Flyway Migration
* Lombok
* MapStruct 
* OpenAPI (Swagger)
* Heroku

## Getting Started 🚀

Clone the repo:
```bash
git clone https://github.com/JustLearningMX/my-notes-api-v2.git
```

### Pre-requisites 📋

* Go to dir `/backend`
  ```bash
    cd backend
    ```
* Environment variables required (example values) to run the application locally in the file `application.properties`:
  ```properties
  DB_URL=jdbc:mysql://localhost:3306/
  DB_NAME=noteDB
  DB_USERNAME=admin
  DB_PASSWORD=my_pa$$word
  JWT_ISSUER=notes-app
  JWT_SECRET=my_secret
  ```

### Usage ⏯
1. Create a database in MySQL with the name `noteDB`.
2. Run the application from the IDE.
3. Open the browser and go to the URL `http://localhost:8080/swagger-ui/index.html` or use the demo on Heroku shown below on section Demo 👨‍💻.
4. Use the Swagger UI to test the API.
5. Use this credentials to login:
    ```json
    {
    "username": "john.doe@gmail.com",
    "password": "$uper-pass123"
    }
    ```
   **Note 1:** The password is encrypted with BCrypt. <br>
   **Note 2:** You can create your own user with the endpoint `POST /users`.
6. Get the JWT token from the response and use it in the `Authorize` button in the Swagger UI.
7. Use the token in the format `Bearer`.
8. Create a note with the endpoint `POST /notes` and the following body:
    ```json
    {
    "title": "My note 1",
    "description": "This is my first note",
    "lastEdited": "2023-08-10T23:46:59.811Z",
    "state": "ACTIVE",
    "deleted": false
    }
    ```
9. Update a note with the endpoint `PUT /notes` and the following body:
    ```json
    {
    "id": 1, 
    "title": "My note 1 updated",
    "description": "This first note was updated",
    "lastEdited": "2023-08-10T23:46:59.811Z",
    "state": "ACTIVE",
    "deleted": false
    }
    ```
10. Archived a note with the endpoint `PUT /notes/archived/{id}`. The `id` path parameter is required.
11. Unarchived a note with the endpoint `PUT /notes/unarchived/{id}`. The `id` path parameter is required.
12. Delete a note (logical) with the endpoint `DELETE /notes/{id}`. The `id` path parameter is required.
13. Get a specific note with the endpoint `GET /notes/{id}`. The `id` path parameter is required.
    ```json
      {
      "id": 5,
      "title": "Mi nota con tags renovado 2",
      "description": "Esta nota con tags fue modificada, agregandosele 2 tags mas",
      "lastEdited": "2023-08-10T17:38:32.680+00:00",
      "state": "ACTIVE",
      "deleted": false,
      "categories": [
        {
          "id": 1,
          "name": "office"
        },
        {
          "id": 2,
          "name": "home"
        }
      ]
      }
    ```
14. Get all the notes with the endpoint `GET /notes`.
15. Create one o more new categories/tags with the endpoint `POST /categories`.
    ```json
      {
          "note_id": 1,
          "categories": 
        [
            {
              "name": "home"
            },
            {
              "name": "office"
            }
        ]
      }
    ```
16. Get all the categories with the endpoint `GET /categories`.

## Demo 👨‍💻
                                                  
- **Application Demo** on OCI VM: [app-note-api](https://hiram-oci-mty.duckdns.org/my-notes-api-v2/v1)

## Frontend 🖥️

Github Pages: [app-notes](https://justlearningmx.github.io/my-notes-views-v2/)

## Author ✒️

* **Hiram Chávez** - [JustLearningMX](https://github.com/JustLearningMX)

## Contact 📬

* Twitter: [@hiram_ch](https://twitter.com/hiram_ch)
* Email: [hiramchavezlopez@gmail.com](https://twitter.com/hiram_ch)
* LinkedIn: [Hiram Chávez](https://www.linkedin.com/in/hiram-chavez-24126831/)
* Website: [Hiram Chávez](https://hiramchavez.tech)
* GitHub: [JustLearningMX](https://github.com/JustLearningMX)

---

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://choosealicense.com/licenses/mit/
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/hiram-chavez-24126831/
[product-screenshot]: /src/main/resources/static/img/caratula-todolist-app.png