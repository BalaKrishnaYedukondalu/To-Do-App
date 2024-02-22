import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ToDo } from '../model/todo';
import { NgToastService } from 'ng-angular-popup';
import { MatDialog } from '@angular/material/dialog';
import { TodoService } from '../services/todo.service';
import { Router } from '@angular/router';
import { AddtodoComponent } from '../addtodo/addtodo.component';

@Component({
  selector: 'app-searchtodo',
  templateUrl: './searchtodo.component.html',
  styleUrls: ['./searchtodo.component.css'],
})

export class SearchtodoComponent implements OnInit {
  selected = '';
  selected2: any;
  searchedTitle: string = '';
  allToDO: ToDo[] = [];

  constructor(
    private router: Router,
    private todoService: TodoService,
    private toast: NgToastService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getAllToDo();
  }

  @Output()
  sendToContainer: EventEmitter<any> = new EventEmitter();
  searchinput() {
    this.sendToContainer.emit(this.searchedTitle);
  }

  @Output()
  sendToContainer1: EventEmitter<any> = new EventEmitter();
  searchinput1() {
    this.sendToContainer1.emit(this.selected);
  }

  @Output()
  sendToContainer2: EventEmitter<any> = new EventEmitter();
  searchinput2() {
    this.sendToContainer2.emit(this.selected2);
  }

  openDialog() {
    this.dialog.open(AddtodoComponent);
  }

  cleardata() {
    this.searchedTitle = '';
    this.selected = '';
    this.selected2 = this.sendToContainer1.emit(this.selected);
    this.sendToContainer.emit(this.searchedTitle);
    this.sendToContainer2.emit(this.selected2);
  }
  showButton: boolean = false;
  important() {
    this.router.navigateByUrl('/home/viewimportant');
  }

  archive() {
    this.router.navigateByUrl('/home/viewarchive');
  }
  
  complete() {
    this.router.navigateByUrl('/home/viewcompleted');
  }

  deleteAllToDo() {
    const confirmed = window.confirm(
      'Are you sure you want to delete all tasks?'
    );
    if (confirmed) {
      this.todoService.deleteAllToDo().subscribe({
        next: (data) => {
          window.location.reload();
          this.toast.success({
            detail: 'AllToDo Deleted successfully',
            summary: 'AllToDo Deleted successfully',
            duration: 4000,
          });
        },
        error: (err) =>
          this.toast.error({
            detail: 'Due to the network issue All toDo is not deleted',
            summary: 'ERROR',
            duration: 4000,
          }),
      });
    }
  }

  getAllToDo() {
    if (!(this.allToDO == null || this.allToDO.length == 0)) {
      this.showButton = false;
    } else {
      this.showButton = true;
    }
    this.todoService.getAllToDo().subscribe((data) => {
      this.allToDO = data.filter(
        (a: { completed: boolean }) => a.completed == false
      );
      if (!(this.allToDO == null || this.allToDO.length == 0)) {
        this.showButton = false;
      } else {
        this.showButton = true;
      }
    });
  }
}
