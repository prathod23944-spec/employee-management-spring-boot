
const form = document.getElementById("employeeForm");
const tableBody = document.getElementById("employeeTableBody");
const message = document.getElementById("message");

const submitButton = form.querySelector('button[type="submit"]');

let editingEmployeeId = null;

// Create Cancel Update button
const cancelButton = document.createElement("button");
cancelButton.type = "button";
cancelButton.textContent = "Cancel Update";
cancelButton.style.display = "none";
cancelButton.style.backgroundColor = "#64748b";

submitButton.insertAdjacentElement("afterend", cancelButton);


// Get all employees from backend
async function loadEmployees() {
    try {
        const response = await fetch("/api/employees");

        if (!response.ok) {
            throw new Error("Could not fetch employees");
        }

        const employees = await response.json();

        tableBody.innerHTML = "";

        employees.forEach(function(employee) {
            addEmployeeToTable(employee);
        });

    } catch (error) {
        message.textContent = "Error loading employees!";
        message.style.color = "red";
        console.error(error);
    }
}


// Add employee row to table
function addEmployeeToTable(employee) {
    const row = document.createElement("tr");

    const nameCell = document.createElement("td");
    nameCell.textContent = employee.name;

    const emailCell = document.createElement("td");
    emailCell.textContent = employee.email;

    const salaryCell = document.createElement("td");
    salaryCell.textContent = "₹" + employee.salary;

    const actionCell = document.createElement("td");

    // Update button
    const updateButton = document.createElement("button");
    updateButton.textContent = "Update";
    updateButton.type = "button";
    updateButton.style.backgroundColor = "#16a34a";

    updateButton.addEventListener("click", function() {
        editingEmployeeId = employee.id;

        document.getElementById("name").value = employee.name;
        document.getElementById("email").value = employee.email;
        document.getElementById("salary").value = employee.salary;

        submitButton.textContent = "Save Update";
        cancelButton.style.display = "block";

        message.textContent = "Edit employee details and save.";
        message.style.color = "#2563eb";

        form.scrollIntoView({
            behavior: "smooth",
            block: "center"
        });
    });

    // Delete button
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.type = "button";
    deleteButton.style.backgroundColor = "red";

    deleteButton.addEventListener("click", async function() {
        try {
            const response = await fetch(
                "/api/employees/" + employee.id,
                { method: "DELETE" }
            );

            if (!response.ok) {
                throw new Error("Could not delete employee");
            }

            if (editingEmployeeId === employee.id) {
                resetForm();
            }

            message.textContent = "Employee deleted successfully!";
            message.style.color = "green";

            await loadEmployees();

        } catch (error) {
            message.textContent = "Error deleting employee!";
            message.style.color = "red";
            console.error(error);
        }
    });

    actionCell.appendChild(updateButton);
    actionCell.appendChild(deleteButton);

    row.appendChild(nameCell);
    row.appendChild(emailCell);
    row.appendChild(salaryCell);
    row.appendChild(actionCell);

    tableBody.appendChild(row);
}


// Reset form and exit update mode
function resetForm() {
    form.reset();

    editingEmployeeId = null;

    submitButton.textContent = "Add Employee";
    cancelButton.style.display = "none";
}


// Cancel update
cancelButton.addEventListener("click", function() {
    resetForm();

    message.textContent = "Update cancelled.";
    message.style.color = "#64748b";
});


// Add or update employee
form.addEventListener("submit", async function(event) {
    event.preventDefault();

    const employee = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        salary: Number(document.getElementById("salary").value)
    };

    const isUpdating = editingEmployeeId !== null;

    const url = isUpdating
        ? "/api/employees/" + editingEmployeeId
        : "/api/employees";

    const method = isUpdating ? "PUT" : "POST";

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(employee)
        });

        if (!response.ok) {
            throw new Error("Could not save employee");
        }

        message.textContent = isUpdating
            ? "Employee updated successfully!"
            : "Employee added successfully!";

        message.style.color = "green";

        resetForm();

        await loadEmployees();

    } catch (error) {
        message.textContent = isUpdating
            ? "Error updating employee!"
            : "Error adding employee!";

        message.style.color = "red";
        console.error(error);
    }
});


// Load existing employees when page opens
loadEmployees();
