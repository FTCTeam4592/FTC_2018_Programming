package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot4592;

@TeleOp(name = "drive")


public class drive extends Robot4592 {

    @Override
    public void runOpMode() {

        tele();

        waitForStart();

        while (opModeIsActive()) {
            drive(gamepad1.left_stick_y, gamepad1.left_stick_y, gamepad1.left_stick_y, gamepad1.left_stick_y);
            strafe(gamepad1.left_stick_x, gamepad1.left_stick_x, gamepad1.left_stick_x, gamepad1.left_stick_x);
            if (gamepad1.left_trigger > 0) {
                turn(gamepad1.left_trigger, gamepad1.left_trigger, -gamepad1.left_trigger, -gamepad1.left_trigger);
            }
            if (gamepad1.right_trigger > 0) {
                turn(-gamepad1.right_trigger, -gamepad1.right_trigger, gamepad1.right_trigger, gamepad1.right_trigger);
            }
        }
    }

    private void drive(double lf, double lr, double rf, double rr){
        leftFront.setPower(lf);
        leftRear.setPower(lr);
        rightFront.setPower(rf);
        rightRear.setPower(rr);
    }
    private void turn(double lf, double lr, double rf, double rr){
        leftFront.setPower(-lf);
        leftRear.setPower(lr);
        rightFront.setPower(-rf);
        rightRear.setPower(rr);
    }
    private void strafe(double lf, double lr, double rf, double rr){
        leftFront.setPower(lf);
        leftRear.setPower(lr);
        rightFront.setPower(rf);
        rightRear.setPower(rr);
    }

}

