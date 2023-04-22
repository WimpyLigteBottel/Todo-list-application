import axios from "axios";

export async function fetchTasks() {
  let data = axios
    .get("http://localhost:8090/v1/task")
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
    .put("http://localhost:8090/v1/task", task)
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
    .delete(`http://localhost:8090/v1/task/${id}`)
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
    .get(`http://localhost:8090/v1/create`)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });

  return data;
}
