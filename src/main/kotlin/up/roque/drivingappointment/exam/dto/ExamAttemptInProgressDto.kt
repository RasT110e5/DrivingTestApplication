package up.roque.drivingappointment.exam.dto

import com.fasterxml.jackson.annotation.JsonInclude
import up.roque.drivingappointment.question.Question
import up.roque.drivingappointment.question.option.dto.OptionDto

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExamAttemptInProgressDto(
  val id:Int?,
  val selectedOptions: Set<OptionDto>,
  val answeredQuestions: Set<Question?>,
  val approved: Boolean,
  val testInProgress:Boolean
)
