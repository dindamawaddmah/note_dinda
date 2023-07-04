package com.robothijau.todolistapp

data class TodoListResponse(
    val success:Boolean,
    val message:String,
    val data:List<TodoListModel>
)
