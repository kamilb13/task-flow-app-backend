//package com.taskflow.taskflowapp;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.taskflow.taskflowapp.model.Board;
//import com.taskflow.taskflowapp.repository.BoardRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BoardTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Before
//    public void setUp() {
//        boardRepository.deleteAll();
//    }
//
//    @Test
//    public void testCreateBoard() throws Exception {
//        Board board = new Board("Test Board", 1, new ArrayList<>());
//
//        mockMvc.perform(post("/create-board")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(board)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("Test Board"))
//                .andExpect(jsonPath("$.boardCreatorId").value(1));
//    }
//
//    @Test
//    public void testGetBoardsWithoutTasks() throws Exception {
//        Board board1 = new Board("Board 1", 1, new ArrayList<>());
//        Board board2 = new Board("Board 2", 2, new ArrayList<>());
//
//        boardRepository.save(board1);
//        boardRepository.save(board2);
//
//        mockMvc.perform(get("/get-boards")
//                        .param("userId", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(1));
//    }
//}
