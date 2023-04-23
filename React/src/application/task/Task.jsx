import "./Task.css";



function Task(props) {

  let tasks = props.tasks
  
  return (
    <div>
       {
        tasks.map((task) => (

      <input key={task.id}
        className="css-input"
        value={task.message}
        onChange={e => tasks}
      />
        ))
        }
     </div>
  );
}

export default Task;


/*
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
      */