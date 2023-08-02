<template>
  <!-- ref is used so that i can call the function via $refs.SearchView to update tasks -->
  <SearchView
      ref="SearchView"
      @updateParentTasks="setTasks"
  />
  <br/>
  <br/>
  <br/>
  <TaskView
      :propTasks="tasks"
      @refreshParentTasks="refreshTasks"
  />
</template>

<script>
import TaskView from './components/tasks/TaskView.vue'
import SearchView from "./components/search/SearchView.vue";

export default {
  name: 'TodoApplication',
  components: {
    SearchView,
    TaskView
  },
  mounted() {
  },
  data() {
    return {
      filterGroup: null,
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
  }, methods: {
    refreshTasks() {
      // See the ref="SearchView" above
      this.$refs.SearchView.getTasksAndFilter();
    },
    setTasks(value) {
      // This is necessary because i am emitting string
      this.tasks = JSON.parse(value)
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
