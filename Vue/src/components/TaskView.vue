<template>
  <div class="main">
    <div class="body">

      <div v-for="(task, index) in tasks" :key="index">
        <input v-model="task.message" size="50" :class="{ completed: task.completed }">
        <button @click="checkTask(index)">CHECK</button>
        <button @click="removeTask(index)">REMOVE</button>
        <button @click="updateTask(index)">UPDATE</button>
        <br />
        <br />
      </div>
    </div>

    <div class="footer">
      <button @click="createTask()">ADD</button>
    </div>


  </div>
</template>

<script>

import { fetchTasks, updateTask, removeTask, createTask } from './TaskService.js';

export default {
  name: 'TaskView',
  mounted() {
    this.getAllTask()
  },
  data() {
    return {
      counter: 0,
      tasks: [
        {
          "id": 0,
          "message": "example message",
          "created": "2023-04-17T19:36:50.4613385Z",
          "updated": "2023-04-17T19:36:50.4613993Z",
          "completed": false
        }]
    }
  },
  methods: {
    async getAllTask() {
      this.tasks = await fetchTasks()
    },
    async checkTask(index) {
      let checkedTask = this.tasks[index]

      checkedTask.completed = !checkedTask.completed

      updateTask(checkedTask).then(() => {
        this.getAllTask()
      })
    },
    async removeTask(index) {

      let checkedTask = this.tasks[index]
      removeTask(checkedTask.id)
        .then(() => {
          this.getAllTask()
        })
    },
    async updateTask(index) {
      updateTask(this.tasks[index])
        .then(() => {
          this.getAllTask()
        })
    },
    async createTask() {
      createTask().then(() => {
        this.getAllTask()
      })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.completed {
  text-decoration: line-through;
}
</style>
