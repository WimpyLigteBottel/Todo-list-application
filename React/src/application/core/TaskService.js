import axios from "axios";

const baseUrl = `http://localhost:8090`;

/*
Below is teh exampel object of task
{
    "id": 2,
    "message": "abc",
    "created": "2023-04-23T18:45:33.205394Z",
    "updated": "2023-04-29T13:42:37.268949Z",
    "completed": false
}
*/

export async function fetchTasks(filterText, isCompleted) {
  if (!filterText) {
    filterText = ``;
  }

  if (!isCompleted) {
    isCompleted = ``;
  }

  let data = axios
    .get(`${baseUrl}/v1/task?message=${filterText}&completed=${isCompleted}`)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });

  return data;
}

export async function updateTask(task) {
  let data = axios
    .put(`${baseUrl}/v1/task`, task)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });

  return data;
}

export async function removeTask(id) {
  let data = axios
    .delete(`${baseUrl}/v1/task/${id}`)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });

  return data;
}

export async function createTask() {
  let data = axios
    .get(`${baseUrl}/v1/create`)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });

  return data;
}
