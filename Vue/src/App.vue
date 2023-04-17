<template>
  <img alt="Vue logo" src="./assets/logo.png">
    <TaskView/>
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
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
