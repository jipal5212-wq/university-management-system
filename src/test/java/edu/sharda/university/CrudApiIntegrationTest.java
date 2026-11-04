package edu.sharda.university;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:university;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CrudApiIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void clearTables() throws Exception {
        mvc.perform(delete("/api/enrollments/201/1001"));
        mvc.perform(delete("/api/teaches/101/1001"));
        mvc.perform(delete("/api/students/201"));
        mvc.perform(delete("/api/teachers/101"));
        mvc.perform(delete("/api/courses/1001"));
        mvc.perform(delete("/api/departments/1"));
    }

    @Test
    void supportsCrudForAllSixTables() throws Exception {
        mvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"deptId":1,"deptName":"Computer Science","building":"Tech Block","budget":2500000}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deptId").value(1));
        mvc.perform(get("/api/departments/1")).andExpect(status().isOk());
        mvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"deptId":1,"deptName":"Computing","building":"North Block","budget":2600000}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deptName").value("Computing"));

        mvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"teacherId":101,"name":"Aditi Sharma","designation":"Professor","experience":12,"salary":160000,"deptId":1}
                                """))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/teachers/101")).andExpect(status().isOk()).andExpect(jsonPath("$.deptId").value(1));
        mvc.perform(put("/api/teachers/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"teacherId":101,"name":"Aditi S.","designation":"Professor","experience":13,"salary":165000,"deptId":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.experience").value(13));

        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"studentId":201,"name":"Aanya Gupta","city":"Ghaziabad","admissionYear":2022,"cgpa":8.7,"deptId":1}
                                """))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/students/201")).andExpect(status().isOk());
        mvc.perform(put("/api/students/201")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"studentId":201,"name":"Aanya G.","city":"Noida","admissionYear":2022,"cgpa":8.8,"deptId":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Noida"));

        mvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"courseId":1001,"courseTitle":"Data Structures","credits":4,"deptId":1}
                                """))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/courses/1001")).andExpect(status().isOk());
        mvc.perform(put("/api/courses/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"courseId":1001,"courseTitle":"Advanced Data Structures","credits":4,"deptId":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseTitle").value("Advanced Data Structures"));

        mvc.perform(post("/api/teaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"teacherId":101,"courseId":1001}
                                """))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/teaches/101/1001")).andExpect(status().isOk());
        mvc.perform(put("/api/teaches/101/1001")).andExpect(status().isOk());

        mvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"studentId":201,"courseId":1001,"marks":88,"attendance":90}
                                """))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/enrollments/201/1001")).andExpect(status().isOk());
        mvc.perform(put("/api/enrollments/201/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"studentId":201,"courseId":1001,"marks":91,"attendance":95}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marks").value(91));

        mvc.perform(delete("/api/enrollments/201/1001")).andExpect(status().isNoContent());
        mvc.perform(delete("/api/teaches/101/1001")).andExpect(status().isNoContent());
        mvc.perform(delete("/api/students/201")).andExpect(status().isNoContent());
        mvc.perform(delete("/api/teachers/101")).andExpect(status().isNoContent());
        mvc.perform(delete("/api/courses/1001")).andExpect(status().isNoContent());
        mvc.perform(delete("/api/departments/1")).andExpect(status().isNoContent());
    }

    @Test
    void rejectsDuplicateIdsAndMissingRecords() throws Exception {
        mvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"deptId":2,"deptName":"Mathematics"}
                                """))
                .andExpect(status().isCreated());
        mvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"deptId":2,"deptName":"Duplicate"}
                                """))
                .andExpect(status().isConflict());
        mvc.perform(get("/api/departments/999")).andExpect(status().isNotFound());
    }
}
