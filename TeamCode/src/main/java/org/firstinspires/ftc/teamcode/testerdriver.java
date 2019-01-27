package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot4592;

@TeleOp(name = "testerdriver", group="Test")
@Disabled

public class testerdriver extends Robot4592 {

    @Override
    public void runOpMode() {

        tele();

        waitForStart();

        while (opModeIsActive()) {

           leftFront.setPower(-1);
           leftRear.setPower(-1);
           rightFront.setPower(1);
           rightRear.setPower(1);
        }
    }

    private void drive(double lf, double lr, double rf, double rr){
        tele();
        leftFront.setPower(lf);
        leftRear.setPower(lr);
        rightFront.setPower(rf);
        rightRear.setPower(rr);
    }
    private void turn(double lf, double lr, double rf, double rr){
        tele();
        leftFront.setPower(-lf);
        leftRear.setPower(lr);
        rightFront.setPower(-rf);
        rightRear.setPower(rr);
    }
    private void strafe(double lf, double lr, double rf, double rr){
        tele();
        leftFront.setPower(lf);
        leftRear.setPower(lr);
        rightFront.setPower(rf);
        rightRear.setPower(rr);
    }

}

