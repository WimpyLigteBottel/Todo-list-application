<template>
  <div class="main">
    <div class="body">


      <div>
        <input v-model="this.filterText" size="50" @keydown.enter="getAllTask()">
        <button :class="{ 'coolButton': true }" @click="getAllTask()">SEARCH</button>
        <button :class="{ 'coolButton': true }" @click="clearFilter()">CLEAR</button>
      </div>

      <br />
      <br />
      <br />

      <div v-for=" (task, index) in tasks" :key="index">
        <div>
          <input v-model="task.message" size="50" :class="{ 'completed': task.completed, 'changed': task.hasUpdated, 'css-input': true }" @keydown="markTaskAsUnsaved(index)"
            @keydown.enter="updateTask(index)">

          <div>
            <button :class="{ 'coolButton': true }" @click="checkTask(index)">CHECK</button>
            <button :class="{ 'coolButton': true }" @click="removeTask(index)">REMOVE</button>
            <button :class="{ 'coolButton': true }" @click="updateTask(index)">UPDATE</button>
          </div>
          <br />
          <br />
        </div>

      </div>
    </div>

    <div class="footer">
      <button :class="{ 'coolButton': true, 'coolButton-green': true }" @click="createTask()">ADD</button>
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
      filterText: '',
      tasks: [
        {
          "id": 0,
          "message": "example message",
          "created": "2023-04-17T19:36:50.4613385Z",
          "updated": "2023-04-17T19:36:50.4613993Z",
          "completed": false,
          "hasUpdated": false
        }]
    }
  },
  methods: {
    async getAllTask() {
      this.tasks = await fetchTasks(this.filterText)

      // resets the hasUpdated field for each task
      this.tasks.forEach((task) => {
        task["hasUpdated"] = false
      })

      if (this.filterText.length == 0)
        return


      this.tasks = this.tasks.filter((task) => {
        console.log('filtering')
        return task.message.includes(this.filterText)
      })
    },
    async checkTask(index) {
      let task = this.tasks[index]
      task.completed = !task.completed

      await updateTask(task)
      await this.getAllTask()
    },
    async removeTask(index) {
      let task = this.tasks[index]
      await removeTask(task.id)
      await this.getAllTask()
    },
    async updateTask(index) {
      await updateTask(this.tasks[index])
      await this.getAllTask()
    },
    async createTask() {
      await createTask()
      await this.getAllTask()
    },
    markTaskAsUnsaved(index) {
      let task = this.tasks[index]
      task.hasUpdated = true
    },
    clearFilter() {
      this.filterText = ''
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.notVisible {
  display: none;
}

.completed {
  text-decoration: line-through;
}

.changed {
  color: red;
}

.css-input {
  width: 50%;
  margin-right: 1%;
  font-size: calc(15px + 0.390625vw);
}

.coolButton {
  appearance: none;
  background-color: #000000;
  border: 2px solid #1A1A1A;
  border-radius: 15px;
  box-sizing: border-box;
  color: #FFFFFF;
  cursor: pointer;
  display: inline-block;
  font-family: Roobert, -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  font-size: calc(15px + 0.390625vw);
  font-weight: 600;
  line-height: normal;
  margin: 0;
  outline: none;
  padding: 16px 3px;
  text-align: center;
  text-decoration: none;
  transition: all 300ms cubic-bezier(.23, 1, 0.32, 1);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: 10%;
  will-change: transform;
  min-width: 80px;
}

.coolButton-green {
  background-color: #28b925;
  border: 2px solid #1A1A1A;
}

.coolButton:disabled {
  pointer-events: none;
}

.coolButton:hover {
  box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
  transform: translateY(-2px);
}

.coolButton:active {
  box-shadow: none;
  transform: translateY(0);
}

.button-group {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-left: auto;
}

.button-group>* {
  margin: 0.5rem;
}

@media screen and (max-width: 750px) {
  .coolButton {
    min-width: 80px;
  }

  .css-input {
    width: 80%;
  }

  .button-group {
    order: 2;
    width: 100%;
    margin-left: 0;
  }
}
</style>
