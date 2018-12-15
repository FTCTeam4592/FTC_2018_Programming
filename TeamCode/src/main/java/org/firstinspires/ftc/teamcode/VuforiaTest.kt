package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix
import org.firstinspires.ftc.robotcore.external.matrices.VectorF
import org.firstinspires.ftc.robotcore.external.navigation.*


@Autonomous(name = "Track Image", group = "Vision")
class VuforiaTest : LinearOpMode() {

    override fun runOpMode() {

        var lastLocation: OpenGLMatrix? = null

        val cameraMonitorViewId: Int = hardwareMap.appContext.resources.getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.packageName
        )
        val parameters: VuforiaLocalizer.Parameters = VuforiaLocalizer.Parameters(cameraMonitorViewId)

        parameters.vuforiaLicenseKey =
                "AR28rTb/////AAAAGdmyxbWoaEUfpgbw+HH9dAR6pd2GE0zLPpsObm9c3iyHvFxLGIrvMEkriYpEMFXybIOF1ng9sKMrJr1He8aXsUQ+7zxItkmGs69z3vTyLgRRD0eUrIJXViYt+tk6IzPYE+4Z9v5hWKteebG3TfzVmT/H/kg6vMLzQblDYNcz4JJZYrCq2axfHBhrDp6ljJQv0esY5DacKVrFLn1H6Jkaxe0vuZFsOveYpTzRdY4v4UuXqEwUxz+NdM/++RZncWkbftEpcLaf1tMFkTZBCOdQ5Tx+HXoT1bpepy1hHF1E6+cwxiUxZAx1ZxbsH5IJ+TfVtk2GjGD1R9CqSqvDE+8fWY12BOZP3PTSdHLaVgCmw/hq"

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK

        val vuforia: VuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters)

        val trackables: VuforiaTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark")
        val template: VuforiaTrackable = trackables[0]

        telemetry.addData(">", "Press Play To Start")
        telemetry.update()
        waitForStart()

        trackables.activate()

        while (opModeIsActive()) {

            val mark: RelicRecoveryVuMark = RelicRecoveryVuMark.from(template)
            if (mark !== RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", mark)

                val pose: OpenGLMatrix? = (template.listener as VuforiaTrackableDefaultListener).pose
                telemetry.addData("Pose", format(pose))

                if (pose != null) {
                    val translation: VectorF = pose.translation
                    val rotation: Orientation = Orientation.getOrientation(
                            pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES
                    )

                    val tX: Double = translation.get(0).toDouble()
                    val tY: Double = translation.get(1).toDouble()
                    val tZ: Double = translation.get(2).toDouble()

                    val rX: Double = rotation.firstAngle.toDouble()
                    val rY: Double = rotation.secondAngle.toDouble()
                    val rZ: Double = rotation.thirdAngle.toDouble()
                }
            } else {
                telemetry.addData("VuMark", "not visible")
            }

            telemetry.update()
        }
    }

    private fun format(transformationMatrix: OpenGLMatrix?): String {
        return if (transformationMatrix != null) transformationMatrix.formatAsTransform() else "null"
    }

}