<template>
  <div>
    <label><input @change="handleChange()" type="radio" value="" checked v-model="filterGroup"> All</label>
    <label><input @change="handleChange()" type="radio" value="true" v-model="filterGroup"> Completed</label>
    <label><input @change="handleChange()" type="radio" value="false" v-model="filterGroup"> Uncompleted</label>
  </div>
  <img alt="Vue logo" src="./assets/todo.png" class="logo">
  <TaskView :filterGroup="filterGroup" ref="TaskView" />
</template>

<script>
import TaskView from './components/TaskView.vue'

export default {
  name: 'App',
  components: {
    TaskView
  },
  data() {
    return {
      filterGroup: null
    }
  },
  methods: {
    async handleChange() {
      await this.$refs.TaskView.getAllTask();
    }
  }
}
</script>

<style>
body {
  background-image: url("https://www.freecodecamp.org/news/content/images/size/w2000/2021/06/w-qjCHPZbeXCQ-unsplash.jpg");
  background-color: #cccccc;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.logo {
  width: 100px;
  height: 100px;
}
</style>
