package up.roque.drivingappointment.exam

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.question.QuestionService
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class ExamService(
  private val examAttemptRepository: ExamAttemptRepository,
  private val appointmentService: AppointmentService,
  private val questionService: QuestionService
) {

  @Transactional
  fun startExamWithGlasses(key: UUID, username: String): ExamAttempt {
    if (findExamAttemptBySecretKeyAndUsername(key, username).isEmpty)
      appointmentService.reserveRandomEyeAppointment(username)
    return startExam(key, username)
  }

  @Transactional
  fun startExam(key: UUID, username: String): ExamAttempt {
    val appointment = appointmentService.getValidAppointmentBySecretAndUsername(key, username)
    return getExamAttempt(appointment)
  }

  @Transactional
  fun selectOptionOnExamWithKeyAndStudent(key: UUID, username: String, optionId: Int): ExamAttempt {
    val examAttempt = getValidExamAttemptFor(key, username)
    examAttempt.addSelectedOption(questionService.getOption(optionId))
    if (examIsCompletedAndFailed(examAttempt)) appointmentService.reserveDrivingTestAppointment(username)
    return examAttemptRepository.save(examAttempt)
  }

  @Transactional
  fun deleteAll(exams: MutableList<ExamAttempt>) {
    examAttemptRepository.deleteAll(exams)
  }

  fun getValidExamAttemptFor(key: UUID, username: String): ExamAttempt {
    val validExamAttempt = findExamAttemptBySecretKeyAndUsername(key, username)
      .orElseThrow { NoValidExamAttemptForUsername(key, username) }
    if (!validExamAttempt.isValidNow()) throw ExamIsNoLongerValidForModification()
    return validExamAttempt
  }

  @StudentAuthorized
  fun findAllForStudent(username: String): List<ExamAttempt> {
    return examAttemptRepository.findAllByStudent(username)
  }

  fun findAllByAppointments(appointments: List<DrivingTestAppointment>): List<ExamAttempt> {
    return examAttemptRepository.findAllByAppointmentIn(appointments)
  }

  fun findExamsOlderThan(days: Long): MutableList<ExamAttempt> {
    return examAttemptRepository.findAllOlderThan(LocalDateTime.now().minusDays(days))
  }


  private fun getExamAttempt(appointment: DrivingTestAppointment): ExamAttempt {
    val examAttemptOptional = examAttemptRepository.findByAppointment(appointment)
    return if (examAttemptOptional.isPresent) examAttemptOptional.get()
    else createNewExamAttempt(appointment)
  }

  private fun createNewExamAttempt(appointment: DrivingTestAppointment): ExamAttempt {
    appointmentService.reportStudentAttendance(appointment)
    val examAttempt = ExamAttempt()
    examAttempt.appointment = appointment
    return examAttemptRepository.save(examAttempt)
  }

  private fun findExamAttemptBySecretKeyAndUsername(key: UUID, username: String) =
    examAttemptRepository.findByAppointmentKeyAndStudent(key, username)

  private fun examIsCompletedAndFailed(examAttempt: ExamAttempt) =
    examAttempt.getRespondedQuestions().size == 10 && !examAttempt.isApproved()

  fun findAll(): List<ExamAttempt> {
    return examAttemptRepository.findAll()
  }

  class NoValidExamAttemptForUsername(key: UUID, username: String) :
    RuntimeException("There is no valid exam attempt with key:$key, and assigned to student:$username")

  class ExamIsNoLongerValidForModification() :
    RuntimeException("This exam is no longer during the valid timeframe for modification (during the appointment)")
}