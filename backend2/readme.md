# Todo list backend application

## Introduction

This is a Kotlin application that uses Spring Boot to expose a REST API for managing tasks.

## Building and Running with Docker

To build and run the application with Docker, you will need to have Docker and Docker Compose installed on your machine.

1. Clone the repository
2. Navigate to the project directory:

3. Build the application with Maven:
   `mvn clean package`

4. Build the Docker images using Docker Compose:
   `docker-compose --build`

5. Access the API at `http://localhost:8090/v1/tasks`

## Dockerfile

The `Dockerfile` is used to build a Docker image for the application. It uses the Azul Zulu OpenJDK 17 image as a base image and copies the application JAR file into the image. To build the image, run the following command in the project directory:
docker build -t my-application .

## Docker Compose

The `docker-compose.yml` file is used to define the services that make up the application. It includes a service for the application and a service for a MySQL database. To build and start the services, run the following command in the project directory:
docker-compose up --build

## contract yaml

If you go to the following location in code you will  find the contract  and how to access it

`/src/main/resources/Contract.yml`

Example:

```yaml
  /v1/task:
    get:
      summary: Get all tasks
      parameters:
        - in: query
          name: ids
          schema:
            type: array
            items:
              type: integer
          description: List of task ids to retrieve
        - in: query
          name: message
          schema:
            type: string
          description: Filter by task message
        - in: query
          name: isBefore
          schema:
            type: string
            format: date
          description: Filter tasks that were created before the specified date
        - in: query
          name: isAfter
          schema:
            type: string
            format: date
          description: Filter tasks that were created after the specified date
        - in: query
          name: completed
          schema:
            type: boolean
          description: Filter tasks that are completed or not
      responses:
        '200':
          description: List of tasks
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/TaskModel'
    post:
      summary: Create a new task
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/CreateTaskRequest'
      responses:
        '200':
          description: The created task
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TaskModel'
    put:
      summary: Update a task
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/TaskModel'
      responses:
        '200':
          description: The updated task
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TaskModel'
  /v1/task/{id}:
    get:
      summary: Get task by id
      parameters:
        - in: path
          name: id
          schema:
            type: integer
          required: true
          description: The task id
      responses:
        '200':
          description: The task
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TaskModel'
        '404':
          description: Task not found
    delete:
      summary: Delete a task
      parameters:
        - in: path
          name: id
          schema:
            type: integer
          required: true
          description: The task id
      responses:
        '204':
          description: Task deleted successfully
```

