<template>
  <div>
    <img alt="Vue logo" src="@/assets/todo.png" class="logo">
    <div>
      <label><input @change="getTasksAndFilter" type="radio" value="" checked v-model="filterGroup"> All</label>
      <label><input @change="getTasksAndFilter" type="radio" value="true" v-model="filterGroup"> Completed</label>
      <label><input @change="getTasksAndFilter" type="radio" value="false" v-model="filterGroup"> Uncompleted</label>
    </div>
    <input v-model="filterText" :class="{ 'css-input': true }" size="50" @change="getAllTask()">
    <div>
      <button :class="{ 'coolButton': true }" @click="getTasksAndFilter()">SEARCH</button>
      <button :class="{ 'coolButton': true }" @click="clearFilter()">CLEAR</button>
    </div>
  </div>
</template>

<script>

import {fetchTasks} from '../TaskService.js';

export default {
  name: 'SearchView',
  mounted() {
    this.getTasksAndFilter()
  },
  emits: ["updateParentTasks"],
  data() {
    return {
      filterGroup: null,
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
    async getTasksAndFilter() {
      await this.getAllTask()
      await this.filterTasks()
      // I am emitting this to the parent to make use of the value NOTE: some reason object did not work thus i am jsonfying it
      this.$emit("updateParentTasks", JSON.stringify(this.tasks))
    },
    async getAllTask() {
      this.tasks = await fetchTasks(this.filterText, this.filterGroup)

      // resets the hasUpdated field for each task
      this.tasks.forEach((task) => {
        task["hasUpdated"] = false
      })
    },
    async filterTasks() {
      if (this.tasks.length === 0)
        return

      this.tasks = this.tasks.filter((task) => {
        let message = task.message.toLowerCase()
        let filter = this.filterText.toLowerCase()
        return message.includes(filter)
      })
    },
    clearFilter() {
      this.filterText = ''
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.css-input {
  width: 50%;
  margin-right: 1%;
  font-size: calc(13px + 0.390625vw);
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
  font-size: calc(10px + 0.390625vw);
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

.button-group > * {
  margin: 0.5rem;
}

@media screen and (max-width: 750px) {
  .coolButton {
    min-width: 80px;
  }

  .css-input {
    width: 80%;
  }
}
</style>
