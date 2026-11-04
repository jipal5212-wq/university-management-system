const resources = {
    departments: {
        label: "Departments",
        endpoint: "/api/departments",
        key: ["deptId"],
        fields: [
            ["deptId", "Department ID", "number", true],
            ["deptName", "Department name", "text", true],
            ["building", "Building", "text", false],
            ["budget", "Budget", "decimal", false]
        ]
    },
    teachers: {
        label: "Teachers",
        endpoint: "/api/teachers",
        key: ["teacherId"],
        fields: [
            ["teacherId", "Teacher ID", "number", true],
            ["name", "Name", "text", true],
            ["designation", "Designation", "text", false],
            ["experience", "Experience (years)", "number", false],
            ["salary", "Salary", "decimal", false],
            ["deptId", "Department ID", "number", false]
        ]
    },
    students: {
        label: "Students",
        endpoint: "/api/students",
        key: ["studentId"],
        fields: [
            ["studentId", "Student ID", "number", true],
            ["name", "Name", "text", true],
            ["city", "City", "text", false],
            ["admissionYear", "Admission year", "number", false],
            ["cgpa", "CGPA", "decimal", false],
            ["deptId", "Department ID", "number", false]
        ]
    },
    courses: {
        label: "Courses",
        endpoint: "/api/courses",
        key: ["courseId"],
        fields: [
            ["courseId", "Course ID", "number", true],
            ["courseTitle", "Course title", "text", true],
            ["credits", "Credits", "number", false],
            ["deptId", "Department ID", "number", false]
        ]
    },
    teaches: {
        label: "Teacher-course assignments",
        endpoint: "/api/teaches",
        key: ["teacherId", "courseId"],
        fields: [
            ["teacherId", "Teacher ID", "number", true],
            ["courseId", "Course ID", "number", true]
        ]
    },
    enrollments: {
        label: "Enrollments",
        endpoint: "/api/enrollments",
        key: ["studentId", "courseId"],
        fields: [
            ["studentId", "Student ID", "number", true],
            ["courseId", "Course ID", "number", true],
            ["marks", "Marks", "decimal", false],
            ["attendance", "Attendance", "decimal", false]
        ]
    }
};

const nav = document.querySelector("#resource-nav");
const title = document.querySelector("#resource-title");
const notice = document.querySelector("#notice");
const editor = document.querySelector("#editor");
const editorTitle = document.querySelector("#editor-title");
const form = document.querySelector("#record-form");
const fieldsContainer = document.querySelector("#form-fields");
const tableHead = document.querySelector("#table-head");
const tableBody = document.querySelector("#table-body");
const recordCount = document.querySelector("#record-count");
let current = "departments";
let editing = false;
let editingRecord = null;

function showNotice(message, isError = false) {
    notice.textContent = message;
    notice.classList.toggle("error", isError);
}

function endpointFor(resource, record) {
    const config = resources[resource];
    return config.endpoint + "/" + config.key.map((key) => encodeURIComponent(record[key])).join("/");
}

function renderNavigation() {
    for (const [key, config] of Object.entries(resources)) {
        const button = document.createElement("button");
        button.type = "button";
        button.className = "nav-item";
        button.textContent = config.label;
        button.addEventListener("click", () => selectResource(key));
        nav.append(button);
    }
}

function selectResource(key) {
    current = key;
    const config = resources[current];
    title.textContent = config.label;
    document.querySelectorAll(".nav-item").forEach((button, index) => {
        button.classList.toggle("active", Object.keys(resources)[index] === key);
    });
    closeEditor();
    showNotice("");
    loadRecords();
}

function renderFields(record = {}) {
    fieldsContainer.replaceChildren();
    const config = resources[current];
    for (const [name, label, type, required] of config.fields) {
        const wrapper = document.createElement("label");
        wrapper.className = "field";
        wrapper.textContent = label;
        const input = document.createElement("input");
        input.name = name;
        input.type = type === "decimal" ? "number" : type;
        if (input.type === "number") input.step = type === "decimal" ? "any" : "1";
        input.required = required;
        input.value = record[name] ?? "";
        input.disabled = editing && config.key.includes(name);
        wrapper.append(input);
        fieldsContainer.append(wrapper);
    }
}

function openEditor(record) {
    editing = Boolean(record);
    editingRecord = record;
    editorTitle.textContent = editing ? "Edit record" : "New record";
    renderFields(record || {});
    editor.hidden = false;
    editor.scrollIntoView({ behavior: "smooth", block: "start" });
}

function closeEditor() {
    editor.hidden = true;
    editing = false;
    editingRecord = null;
    form.reset();
    fieldsContainer.replaceChildren();
}

async function loadRecords() {
    tableHead.replaceChildren();
    tableBody.replaceChildren();
    recordCount.textContent = "Loading records…";
    const config = resources[current];
    for (const [name, label] of config.fields) {
        const heading = document.createElement("th");
        heading.textContent = label;
        tableHead.append(heading);
    }
    const actionsHeading = document.createElement("th");
    actionsHeading.textContent = "Actions";
    tableHead.append(actionsHeading);

    try {
        const response = await fetch(config.endpoint);
        if (!response.ok) throw new Error(await errorMessage(response));
        const records = await response.json();
        recordCount.textContent = `${records.length} ${records.length === 1 ? "record" : "records"}`;
        if (records.length === 0) {
            const row = document.createElement("tr");
            const cell = document.createElement("td");
            cell.colSpan = config.fields.length + 1;
            cell.className = "empty";
            cell.textContent = "No records found.";
            row.append(cell);
            tableBody.append(row);
            return;
        }
        for (const record of records) {
            const row = document.createElement("tr");
            for (const [name] of config.fields) {
                const cell = document.createElement("td");
                cell.textContent = record[name] ?? "—";
                row.append(cell);
            }
            const actions = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.type = "button";
            editButton.className = "button quiet";
            editButton.textContent = "Edit";
            editButton.addEventListener("click", () => openEditor(record));
            const deleteButton = document.createElement("button");
            deleteButton.type = "button";
            deleteButton.className = "button danger";
            deleteButton.textContent = "Delete";
            deleteButton.addEventListener("click", () => deleteRecord(record));
            actions.append(editButton, deleteButton);
            row.append(actions);
            tableBody.append(row);
        }
    } catch (error) {
        recordCount.textContent = "Could not load records";
        showNotice(error.message, true);
    }
}

async function errorMessage(response) {
    try {
        const body = await response.json();
        return body.detail || body.message || `Request failed (${response.status})`;
    } catch {
        return `Request failed (${response.status})`;
    }
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const config = resources[current];
    const wasEditing = editing;
    const payload = {};
    for (const [name, , type] of config.fields) {
        const value = config.key.includes(name) && editing
            ? editingRecord[name]
            : new FormData(form).get(name);
        payload[name] = value === "" ? null : type === "text" ? value : Number(value);
    }
    const requestUrl = wasEditing ? endpointFor(current, payload) : config.endpoint;
    try {
        const response = await fetch(requestUrl, {
            method: wasEditing ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        if (!response.ok) throw new Error(await errorMessage(response));
        closeEditor();
        showNotice(wasEditing ? "Record updated." : "Record created.");
        await loadRecords();
    } catch (error) {
        showNotice(error.message, true);
    }
});

async function deleteRecord(record) {
    if (!window.confirm("Delete this record?")) return;
    try {
        const response = await fetch(endpointFor(current, record), { method: "DELETE" });
        if (!response.ok) throw new Error(await errorMessage(response));
        showNotice("Record deleted.");
        await loadRecords();
    } catch (error) {
        showNotice(error.message, true);
    }
}

document.querySelector("#new-record").addEventListener("click", () => openEditor(null));
document.querySelector("#cancel-edit").addEventListener("click", closeEditor);
document.querySelector("#refresh").addEventListener("click", loadRecords);
renderNavigation();
selectResource(current);
