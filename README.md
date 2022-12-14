# Advance Programming Techniques TP

## Project description and tips

This is a Spring Boot Rest API developed in Kotlin to provide services according to bellow's objectives.
An initial class and use case diagram can be found in the root of the
project ([drawio diagram](/Class%20Diagram.drawio)).

- Tips
    - Even though it only has 1 profile (dev) to load development data in an in memory DB, it can also connect to a
      mysql db if details are provided in [mysql properties file,](/src/main/resources/application-mysql.yml) and it is
      run with mysql and dev profiles.
    - 2 file can be found under [http folder](/http), one for admin use cases and one for student use cases. This is useful to manually test the application, and to demo it.
    - If at any point you want to reset the data back to an initial step just restart the application.

## Objectives

Create a system for management of driving licenses test appointments, with the following functional requirements:

1. When starting the exam the user must answer if they use glasses.
    1. If so, the system should generate an eye appointment at a random time and start the exam.
    2. If not, the system should just start the exam.
2. The user must use a generated key to enter the exam, this key should expire after 1 hour and should not be usable if
   the exam is not finished during the appointment.
3. The driving test should consist of 10 multiple choice questions, each with only 1 correct option.
    1. The questions should be sorted in a random order at the start of the exam.
    2. The exam is approved with 8 questions answered correctly
        1. If the exam is failed, then the system should generate a random new appointment for retry.
    3. The exam can only be taken 3 times by the same user.
    4. The questions' and options' text must be modifiable by the admins
4. The exam results must only be kept for a week
    1. The admins should be able to get statistics of said results grouped by day with:
        1. Amount of exams
        2. Amount of passed exams
        3. Amount of failed exams
        4. Appointments with students as no-show

### Assignment in Spanish

#### Primera Entrega

Se requiere realizar un sistema de gesti??n para la asignaci??n de turno y entrega de registro de conducci??n.

#### Objetivos

Objetivo: realizar el control de turnos y otorgamiento de licencias de conducir, tomando en consideraci??n los siguientes
requerimientos funcionales.

1. El examen consta de dos etapas.
    1. La primera etapa incluye la pregunta si utiliza o no anteojos.
        1. Si responde que no autom??ticamente, deber?? generarse el examen y permitirle responder el cuestionario.
        2. Si responde que si, se generar?? un turno random para que asista a una revisi??n. (La fecha deber?? generarse de
           manera aleatoria).
2. El usuario deber??a generar una clave para poder acceder al examen, la cual tendr?? un tiempo de expiraci??n de una
   hora.
    1. La clave generada podr?? utilizarse una sola vez.
    2. En caso de que no termine la evaluaci??n no podr?? volver a usarla.
3. El cuestionario constar?? de 10 preguntas precargas de opci??n m??ltiple.
    1. Las mismas deber??s cargarse de forma aleatoria.
4. Aprueba el examen con un 8 preguntas respondidas correctamente.
5. En caso de reprobar el sistema deber?? generar un nuevo turno aleatorio.
6. La misma persona solo podr?? rendir un m??ximo de 3 veces el examen.
7. Los resultados del examen deber??n guardarse durante 1 semana.
8. Las preguntas deber??n poder ser editables por un administrador del sistema.
9. El administrador del sistema podr?? acceder a una estad??stica diaria de cuantos rindieron el examen, cuantos
   aprobaron, reprobaron o estuvieron ausentes.

**Se pide:**

- Implementar un modelo cliente servidor.
- Implementar modelo API, desacoplar completamente el frontend del backend.
- Implementar el paradigma de objetos.
- Implementar al menos en la relaci??n entre dos objetos inyecci??n de dependencias.

#### Objetivo

Se pide documentar el plan de trabajo y las decisiones t??cnicas tomadas para la resoluci??n del mismo. Se deber??n
entregar diagramas y justificaciones. **NO** se requiere la entrega de c??digo en la primera instancia.

#### Requerimientos

- Diagrama de clases del sistema.
- Modelo de datos.
- Detalle de las tecnolog??as a utilizar.
- Tipo de testeo y m??dulos que se planifican testear.
- Justificaci??n de la elecci??n de tecnolog??a y consideraciones.

#### Condiciones De Entrega

Deber?? entregarse un documento en formato PDF conteniendo todos los requerimientos expuestos.

**Fecha de entrega:** semana 8

En el caso de que no se pueda realizar la entrega en la fecha estipulada, deber?? entregar todo junto en la fecha de
entrega del proyecto integrador. Si en esa instancia no alcanza a cumplir con los objetivos, podr?? realizar la reentrega
en la instancia de recuperaci??n correspondiente.

En el caso de que se realicen observaciones o requerir correcciones en el primer entregable, estos deber??n entregarse
resueltos en la segunda entrega.

### Segunda Entrega

#### Objetivo

desarrollar y desplegar la soluci??n planteada en el primer entregable.

#### Consideraciones

- El c??digo puede ser desarrollado seleccionando alguna de los siguiente lenguajes: phyton, java, Javascript.
- Para la persistencia de la informaci??n se puede elegir bases de datos relacionales o no relacionales, justificando su
  elecci??n.
- Se espera que se desplieguen los conceptos revisados durante la cursada (tales como la orientaci??n a servicios), por
  lo que no ser??n consideradas correctas las soluciones monol??ticas.
- Se deber?? presentar la herramienta utilizada para los tests unitarios a partir de la tecnolog??a de trabajo
  seleccionada y los tests escritos.

#### Entregable

- Link al repositorio de c??digo en github o bitbucket seg??n prefiera.
- Se deber?? incluir todo el c??digo desarrollo, un readme con la informaci??n para realizar el despliegue en un servidor y
  consideraciones.

#### Defensa

Esta instancia sincr??nica consistir?? en una demo de la soluci??n funcionando, durante la cual se realizar??n la preguntas
pertinentes que el docente considere sobre la implementaci??n.

#### Evaluaci??n

La nota final se compondr?? del resultado de la evaluaci??n sincr??nica, m??s la revisi??n del c??digo entregado.
