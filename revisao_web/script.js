document.addEventListener('DOMContentLoaded', loadTasks);
document.getElementById('taskForm').addEventListener('submit', function(e) {
    e.preventDefault();  // Evita o comportamento padrão do form de recarregar a página
    addTask();
});

// GET das Tasks salvas no Local Storage
function loadTasks() {
    const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    tasks.forEach(task => {
        renderTask(task.text, task.completed);
    });
}

// POST da nova task
function saveTasks() {
    const tasks = [];
    document.querySelectorAll('#taskList li').forEach(listItem => {
        const text = listItem.querySelector('label').textContent;
        const completed = listItem.classList.contains('completed');
        tasks.push({ text, completed });
    });
    localStorage.setItem('tasks', JSON.stringify(tasks));
}

// Mostra a nova taks na lista
function renderTask(taskText, completed) {
    // pega o <ul></ul>
    const taskList = document.getElementById('taskList');

    // cria um novo item <li></li>
    const listItem = document.createElement('li');

    // cria um novo item checkbox
    const checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    checkbox.checked = completed;

    // se checkbox selecionado, tacha o item como completo e mostra pro usuário
    checkbox.addEventListener('change', function() {
        listItem.classList.toggle('completed');
        saveTasks();
    });

    
    const label = document.createElement('label');
    label.textContent = taskText;

    // cria o botão para deletr uma taks
    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Apagar';
    deleteButton.addEventListener('click', function() {
        taskList.removeChild(listItem);
        saveTasks();
    });

    // seta no <li></li> o checkbox, o label e o botão de delete
    listItem.appendChild(checkbox);
    listItem.appendChild(label);
    listItem.appendChild(deleteButton);

    if (completed) {
        listItem.classList.add('completed');
    }

    // adiciona no <ul></ul> o <li></li> criado
    taskList.appendChild(listItem);
}

// verifica se o input está vazio e se não chama o renderTask(), saveTasks() e seta o input para ''
function addTask() {
    const taskInput = document.getElementById('taskInput');
    const taskText = taskInput.value.trim();

    if (taskText === '') {
        return;
    }

    renderTask(taskText, false);
    taskInput.value = '';
    saveTasks();
}