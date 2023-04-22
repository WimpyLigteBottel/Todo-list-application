<template>
  <img alt="Vue logo" src="./assets/todo.png" class="logo">
  <TaskView />
</template>

<script>
import TaskView from './components/TaskView.vue'

export default {
  name: 'App',
  components: {
    TaskView
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
