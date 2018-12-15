package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp(name = "One Encoder Test", group = "Test")
class OneEncoderTest : LinearOpMode() {

    private var motor: DcMotor? = null

    override fun runOpMode() {
        motor = hardwareMap.dcMotor.get("motor")

        motor?.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor?.mode = DcMotor.RunMode.RUN_USING_ENCODER

        waitForStart()

        while (opModeIsActive()) {
            motor?.power = gamepad1.left_stick_y.toDouble()

            telemetry.addData("Encoder Value: ", motor?.currentPosition)
            telemetry.update()
        }
    }

}