import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { LoginService } from '../services/login.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { ToDo } from '../model/todo';

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.css'],
})
export class PersonalComponent implements OnInit {
  allToDO: ToDo[] = [];
  showMessage: boolean = true;
  p: any;
  selected = '';
  constructor(
    private toDoService: TodoService,
    private userService: LoginService,
    private router: Router,
    private ar: ActivatedRoute,
    private toast: NgToastService
  ) {}

  ngOnInit(): void {
    this.getToDoByPersonalStatus(true);
  }

  

  isOpen = false;
  animateButton() {
    let elem = <HTMLInputElement>document.getElementById('sidebar_sidenav');
    elem.setAttribute('visibility', 'visible');
    if (this.isOpen) this.isOpen = false;
    else this.isOpen = true;
  }

  
  @Output()
  sendToContainer1: EventEmitter<any> = new EventEmitter();

  searchinput1() {
    this.sendToContainer1.emit(this.selected);
  }
  home() {
    this.router.navigateByUrl('/home/header');
  }

  important() {
    this.router.navigateByUrl('/home/viewimportant');
  }
  archive() {
    this.router.navigateByUrl('/home/viewarchive');
  }
  complete() {
    this.router.navigateByUrl('/home/viewcompleted');
  }

  Personal() {
    this.router.navigateByUrl('/home/Personal');
  }

  Travel() {
    this.router.navigateByUrl('/home/Travel');
  }

  getToDoByPersonalStatus(important: boolean) {
    this.toDoService.getToDoByPersonalStatus(important).subscribe((data) => {
      this.allToDO = data.filter(
        (a: { completed: boolean }) => a.completed == false
      );
      if (!(this.allToDO == null || this.allToDO.length == 0)) {
        this.showMessage = false;
      } else {
        this.showMessage = true;
      }
    });
  }

  updateTaskArchiveStatus(card: any) {
    this.toDoService.updateTaskArchiveStatus(card).subscribe({
      next: () => {
        this.toast.success({
          detail: 'Archive ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateTaskAsCompletedTask(card: any) {
    this.toDoService.updateTaskAsCompletedTask(card).subscribe({
      next: () => {
        this.getToDoByPersonalStatus(true);
        this.toast.success({
          detail: 'Completed ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
        console.log('done hai jiii');
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateToDoAsPersonalTask(card: any) {
    this.toDoService.updateToDoAsPersonalTask(card).subscribe({
      next: () => {
        this.getToDoByPersonalStatus(true);
        this.toast.success({
          detail: 'Important ToDo Is Remove',
          summary: 'Remove',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  deleteTaskById(title: any) {
    this.toDoService.deleteToDoByTitle(title).subscribe({
      next: (data) => {
        this.getToDoByPersonalStatus(true);
        this.toast.success({
          detail: 'ToDo Deleted successfully',
          summary: 'DELETED',
          duration: 4000,
        });
        this.allToDO;
      },
      error: (err) =>
        this.toast.error({
          detail: 'Due to the network issue toDo is not deleted',
          summary: 'ERROR',
          duration: 4000,
        }),
    });
  }
  search(title: string) {
    if (title === '' || !title) {
      this.getToDoByPersonalStatus(true);
    } else {
      this.allToDO = this.allToDO;
      this.allToDO = this.allToDO.filter(
        (data) =>
          data.title?.includes(title) || data.description?.includes(title)
      );
    }
  }

  filter(priority: string) {
    if (priority == '') {
      this.getToDoByPersonalStatus(true);
    } else {
      this.toDoService
        .getAllListByPriority(priority)
        .subscribe((data) => (this.allToDO = data));
    }
  }

  completionStatus(isCompleted: boolean) {
    this.toDoService.getToDOByCompletedStatus(isCompleted).subscribe((data) => {
      this.allToDO = data;
    });
  }
}
