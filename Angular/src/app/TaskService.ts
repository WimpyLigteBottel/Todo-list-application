import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";


@Injectable()
export class TaskService {
    constructor(private http: HttpClient) {
    }

    public fetchTask(filterText: string, isCompleted: boolean | null): Observable<Task[]> {
        let baseUrl = `http://localhost:8090/v1/task`;

        if (isCompleted == null) {
            return this.http.get<Array<Task>>(`${baseUrl}?message=${filterText}`)
        }

        return this.http.get<Array<Task>>(`${baseUrl}?message=${filterText}&completed=${isCompleted}`)
    }

    public updateTask(task: Task): Observable<Object> {
        return this.http.put(
            `http://localhost:8090/v1/task`,
            task
        )
    }

    public removeTask(task: Task): Observable<Object> {
        return this.http.delete(
            `http://localhost:8090/v1/task/${task.id}`
        )
    }

    public createTask(): Observable<Object> {
        return this.http.get(`http://localhost:8090/v1/create`)
    }
}


export class Task {
    id: string | "" = "0"
    message: string | "" = ""
    created: string | null = null
    updated: string | null = null
    completed: boolean | false = false
    hasUpdated: boolean | false = false

    constructor(id: string | "", message: string | "") {
        this.id = id;
        this.message = message;
    }

}

export class CreateTaskRequest {
    message: string | "" = ""
}