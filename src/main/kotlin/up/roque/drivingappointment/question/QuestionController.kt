package up.roque.drivingappointment.question

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import up.roque.drivingappointment.question.dto.ModifyQuestionDto
import up.roque.drivingappointment.question.dto.QuestionDto
import up.roque.drivingappointment.question.option.dto.ModifyOptionDto
import up.roque.drivingappointment.question.option.dto.OptionDto
import up.roque.drivingappointment.web.security.AdminAuthorized
import up.roque.drivingappointment.web.BaseRestResponse
import up.roque.drivingappointment.web.security.StudentAuthorized
import up.roque.drivingappointment.web.security.StudentOrAdminAuthorized
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/questions")
class QuestionController(private val questionService: QuestionService) {

  @GetMapping
  @AdminAuthorized
  fun getAllQuestions(): ResponseEntity<BaseRestResponse<MutableList<QuestionDto>>> {
    val questions = questionService.findAllQuestions().stream().map { it.toDto() }.collect(Collectors.toList())
    return BaseRestResponse.ok(questions)
  }

  @GetMapping("/{id}")
  @StudentOrAdminAuthorized
  fun getQuestion(@PathVariable id: Int): ResponseEntity<BaseRestResponse<QuestionDto>> {
    val question = questionService.getQuestion(id)
    return BaseRestResponse.ok(question.toDto())
  }

  @PostMapping
  @AdminAuthorized
  fun changeQuestionText(@RequestBody modifyDto: ModifyQuestionDto): ResponseEntity<BaseRestResponse<QuestionDto>> {
    val modifiedQuestion = questionService.modifyQuestion(modifyDto)
    return BaseRestResponse.ok(modifiedQuestion.toDto())
  }

  @GetMapping("/options")
  @AdminAuthorized
  fun getAllOptions(): ResponseEntity<BaseRestResponse<MutableList<OptionDto>>> {
    val options = questionService.findAllOptions()
    return BaseRestResponse.ok(options.stream().map { it.toDto() }.collect(Collectors.toList()))
  }

  @GetMapping("/options/{id}")
  @AdminAuthorized
  fun getOption(@PathVariable id: Int): ResponseEntity<BaseRestResponse<OptionDto>> {
    val option = questionService.getOption(id)
    return BaseRestResponse.ok(option.toDto())
  }

  @PostMapping("/options")
  @AdminAuthorized
  fun modifyOption(@RequestBody modifyDto: ModifyOptionDto): ResponseEntity<BaseRestResponse<OptionDto>> {
    val option = questionService.modifyOption(modifyDto)
    return BaseRestResponse.ok(option.toDto())
  }
}