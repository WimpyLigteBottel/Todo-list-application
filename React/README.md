# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

# Interesting notes about when i was developing this app in react

### 1. I had to learn about the useState + useEffect. 

Not sure if this is the correct* thing but in my mind its as follows

```javascript
    const [searchFilterText, setSearchFilterText] = useState(""); // instiatiates the variable
    //searchFilterText <<< is get current state
    //setSearchFilterText <<< is the "async*****" set state call


    //This is the hook that will be called bsed on the [dependencies...] your are SPECIFICLY listening too
    // to re-render your compontent
    useEffect(() => {
        //This will do a callback based on the property passed in somewhere
        props.onFilterChange(searchFilterText)
    }, [searchFilterText]);

    // THIS IS ASYNC LIKE SET AND YOU ONLY GET THE MOST UPDATE `searchFilterText` ONCE USEEFFECT RAN!
    setSearchFilterText("THIS IS SET") 
```


### 2. Components feels great the smaller they are!

I did find it made development slightly easier and did not have to worry about BIG complex pages. It 
also forced me to try and make the code simple.

### 3. 1000+ time re render in useEffect

I found that there was interesting case where i had "recursive update" where if i was listening to a 
certain state and i update it setXXXXX it would call the useEffect again.

Example in the `fetchTasks(filterText, selectedOption)` method i get the response then do `setTasks(response)`
which is all good but then, if i was also listening to the `tasks` use state in useEffect it could do recursive* render

1. render
2. do refresh (fetch task)
3. set the task
4. GO TO STEP 1

To prevent this i did 2 things. have simple boolean `doCall` that i would set to false and only do the fetch call if 
it is true then afterwards i will set it to false. This prevent 1000's rerender calls but does cause bit of ugly code
maybe this could be done better but this is my first stab.

```javascript

const [doCall, setDoCall] = useState(false);
const [tasks, setTasks] = useState([]);
const [selectedOption, setSelectedOption] = useState("");
const [filterText, setFilterText] = useState("");

useEffect(() => {
    if (!doCall) {
        return
    }
    fetchTasks(filterText, selectedOption)
        .then((response => {
            setTasks(response);
            setDoCall(false)
        }));
}, [selectedOption, filterText, doCall]);
```


### 4. Experience working with react after WEEKS AWAY 

After a while away of coding in react month plus. (Note: I dont do any frontend work at my current workplace).
I did struggle a bit of rerending my compontents and why it was not working. Overall the callbacks and passing properties in
made a lot of sense and was easy to pick up again.

so i would say `useEffect` + `useState` might need bit of fundemental understanding when doing development work
but otherwise it was pretty great!
