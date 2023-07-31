import {Component, Injectable, OnInit} from '@angular/core';
import {Task, TaskService} from "./TaskService";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent implements OnInit {
    title = 'my-app';
    tasks: Task[] = []
    filterText: string = ""
    isCompleted: boolean | null = null
    isCompletedForm: any;

    constructor(private taskService: TaskService) {
    }

    ngOnInit(): void {
        let filterText = ''
        let isCompleted = false

        this.isCompletedForm = new FormGroup({
            isCompleted: new FormControl(),
        });

        this.updateTasks()
    }

    public updateTasks() {
        console.log(`calling ${this.filterText} ${this.isCompleted}`)
        this.taskService.fetchTask(this.filterText, this.isCompleted)
            .subscribe((data) => {
                this.tasks = data.map(item => item);
            })
    }

    public clear() {
        this.filterText = ''
        this.updateTasks()
    }


    public completeTask(task: Task) {
        task.completed = !task.completed
        this.taskService.updateTask(task)
            .subscribe(response => {
                this.updateTasks()
            })
    }

    public markTaskAsUnsaved(task: Task) {
        task.hasUpdated = true
    }

    public updateSpecificTask(task: Task, message: string) {
        task.message = message
        this.taskService.updateTask(task)
            .subscribe(response => {
                this.updateTasks()
            })
    }

    public removeSpecificTask(task: Task) {
        console.log(`removeSpecificTask`, task)
        this.taskService.removeTask(task)
            .subscribe(response => {
                this.updateTasks()
            })
    }

    public createNewTask() {
        this.taskService.createTask()
            .subscribe(response => {
                this.updateTasks()
            })
    }

    public updateFilter(value: string) {
        if (value == '')
            this.isCompleted = null
        else if (value == 'true')
            this.isCompleted = true
        else if (value == 'false')
            this.isCompleted = false

        this.updateTasks()
    }

    public onKeydownHandler(event: any, task: Task, message: string): void {
        if (event.key === "Enter") {
            this.updateSpecificTask(task, message)
        } else {
            this.markTaskAsUnsaved(task)
        }
    }
}
