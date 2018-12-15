package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "EncoderTest", group = "TeleOp")
class EncoderTest : LinearOpMode() {

    private var leftFront: DcMotor? = null
    private var rightFront: DcMotor? = null
    private var rightRear: DcMotor? = null
    private var leftRear: DcMotor? = null

    override fun runOpMode() {

        leftFront = hardwareMap.dcMotor.get("left_front")
        leftRear = hardwareMap.dcMotor.get("left_rear")
        rightFront = hardwareMap.dcMotor.get("right_front")
        rightRear = hardwareMap.dcMotor.get("right_rear")

        leftFront?.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftRear?.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightFront?.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightRear?.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        leftFront?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        rightRear?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        leftRear?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        rightFront?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        leftFront?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightRear?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        leftRear?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightFront?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        leftFront?.direction = DcMotorSimple.Direction.REVERSE
        leftRear?.direction = DcMotorSimple.Direction.REVERSE

        waitForStart()

        while (opModeIsActive()) {

            leftFront?.power = gamepad1.left_stick_y.toDouble()
            leftRear?.power = gamepad1.left_stick_y.toDouble()
            rightFront?.power = gamepad1.left_stick_y.toDouble()
            rightRear?.power = gamepad1.left_stick_y.toDouble()

            telemetry.addData("Left Front", leftFront?.currentPosition)
            telemetry.addData("Left Rear", leftRear?.currentPosition)
            telemetry.addData("Right Front", rightFront?.currentPosition)
            telemetry.addData("Right Rear", rightRear?.currentPosition)

            telemetry.update()

        }

    }

}
