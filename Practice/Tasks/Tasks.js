let tasks = [];
let completedStatus = [];

const addTask = () => {
    let taskInput = document.getElementById(`tasks`);
    let task = taskInput.value;
    if (!task.trim()) return alert(`Please enter a task`);
    if (tasks.includes(task)) return alert(`Task already exists`);

    tasks.push(task);
    completedStatus.push(false);
    taskInput.value = "";
    displayAllTasks();

    localStorage.setItem(`tasks`, JSON.stringify(tasks));
    localStorage.setItem(`completedStatus`, JSON.stringify(completedStatus));
}

const deleteTask = (index) => {
    let deleteTask = prompt(`Are you sure you want to delete this task?`)
    if (deleteTask === `yes`) {
        tasks.splice(index, 1);
        completedStatus.splice(index, 1);
        displayAllTasks();
        localStorage.setItem(`tasks`, JSON.stringify(tasks));
        localStorage.setItem(`completedStatus`, JSON.stringify(completedStatus));
    }
}

function markAsComplete(index) {
    let confirmation = prompt(`Are you sure you want to mark this task as complete?`);
    if (confirmation === `yes`) {
        completedStatus[index] = true;
        displayAllTasks();
        localStorage.setItem(`completedStatus`, JSON.stringify(completedStatus));
    }
}

function markAsIncomplete(index) {
    let confirmation = prompt(`Are you sure you want to mark this task as incomplete?`);
    if (confirmation === `yes`) {
        completedStatus[index] = false;
        displayAllTasks();
        localStorage.setItem(`completedStatus`, JSON.stringify(completedStatus));
    }
}

const displayAllTasks = () => {
    let taskList = document.getElementById(`taskList`);
    taskList.innerHTML = ``;
    tasks.forEach((task, index) => {
        let taskItem = document.createElement(`div`);
        let isCompleted = completedStatus[index];
        let textDecoration = isCompleted ? 'line-through' : 'none';

        taskItem.innerHTML = `
        <div class="task-content">
            <h1 style="text-decoration: ${textDecoration};">${task}</h1>
        </div>
        <button onclick="deleteTask(${index})">Delete</button>
        <button onclick="${isCompleted ? `markAsIncomplete(${index})` : `markAsComplete(${index})`}">${isCompleted ? 'Mark as Incomplete' : 'Mark as Complete'}</button>
        `;
        taskList.appendChild(taskItem);
    })
}

// Load tasks from localStorage on page load
window.addEventListener('DOMContentLoaded', () => {
    let storedTasks = localStorage.getItem(`tasks`);
    let storedStatus = localStorage.getItem(`completedStatus`);

    if (storedTasks) {
        tasks = JSON.parse(storedTasks);
    }
    if (storedStatus) {
        completedStatus = JSON.parse(storedStatus);
    }

    displayAllTasks();
});

//add event listener to the button
let addButton = document.getElementById(`addButton`);
addButton.addEventListener(`click`, addTask);
