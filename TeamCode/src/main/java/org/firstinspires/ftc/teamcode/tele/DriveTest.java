package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.RobotBase;


/**
 * Created by user on 12/9/17.
 */


@TeleOp(name = "DriveTest")

public class DriveTest extends RobotBase {

    @Override
    public void runOpMode() throws InterruptedException {
        setup();

        waitForStart();


        left_f.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_f.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        final int originalFlipPos = flip_out.getCurrentPosition();


        while (opModeIsActive()) {
            // update

            final int drop_position = 0;
            final int original_position = 3000;
            final int extendUp_Raised = 4000;
            final int extendUp_Lowered = 0;
            final int original_out = extendOut.getCurrentPosition();
            final int original_up = extendUp.getCurrentPosition();
            final double originial_flip_up = flip_up.getPosition();

            //strafe
            double leftFront_Power = -1.5 * Math.cos(gamepad1.left_stick_y) * Math.sin(gamepad1.left_stick_x);
            double rightFront_Power = -leftFront_Power;
            double leftRear_Power = -leftFront_Power;
            double rightRear_Power = leftFront_Power;

            //drive
            double frontRight = -gamepad1.right_stick_y;
            double rearRight = -gamepad1.right_stick_y;
            double frontLeft = gamepad1.left_stick_y;
            double rearLeft = gamepad1.left_stick_y;


            if (gamepad2.x && lift_arm.getCurrentPosition()>= 500) {
                drop(0); //when x is pressed, the lift arm will go down
            }
            if (gamepad2.x && lift_arm.getCurrentPosition() < 500) {
                climb(3200); //when b is pressed, teh lift arm will go up
            }

            if (gamepad2.b && extendUp.getCurrentPosition() <= 1500) {
                extendUp(extendUp_Raised);
            } else if (gamepad2.b && extendUp.getCurrentPosition() >= 1500) {
                extendUp(extendUp_Lowered);
            }

            double ext = 0.5;
            extendOut.setPower(0);
            while(gamepad2.right_bumper) {
                extendOut.setPower(ext);
            }

            while(gamepad2.left_bumper){
                extendOut.setPower(-ext);
            }

            if(gamepad2.dpad_left && flip_out.getCurrentPosition()>=50){
                flip_out.setTargetPosition(0);
                flip_out.setPower(0.75);
            }
            if(flip_out.getCurrentPosition()<=15){
                flip_out.setPower(0);
            }
            if(gamepad2.dpad_right && flip_out.getCurrentPosition()<50){

                flip_out.setTargetPosition(originalFlipPos);
                flip_out.setPower(-0.4);

            }


            if (gamepad2.b && extendUp.getCurrentPosition()<250) {


                extendUp.setTargetPosition(4500);
                extendUp.setPower(1);

            } else if (gamepad2.b && extendUp.getCurrentPosition()>250) {

                extendUp.setTargetPosition(10);
                extendUp.setPower(1);
            }

            while(gamepad2.dpad_up){

                flipUp(0.6);
            }
            while(gamepad2.dpad_down){
                flipUp(0.1);
            }


            intake(0);
            while(gamepad2.a) {
                intake(0.9);
            }

            telemetry.addData("leftfrontpower: ", leftFront_Power);
            telemetry.addData("leftrearpower: ", leftRear_Power);
            telemetry.addData("rightfrontpower: ", rightFront_Power);
            telemetry.addData("rightrearpower: ", rightRear_Power);
            telemetry.addData("lift position", lift_arm.getCurrentPosition());
            telemetry.addData("extension position: ", extendOut.getCurrentPosition());
            telemetry.addData("extend up: ", extendUp.getCurrentPosition());
            telemetry.addData("flipup: ", flip_up.getPosition());
            telemetry.addData("flip out: ", flip_out.getCurrentPosition());
            /*
            if (isGold) {
                telemetry.addLine("IT WORKS BABYŸŸŸŸŸŸ;;;;;;;;;;;;YY");
            } else {
                telemetry.addLine("THIS AIN'T IT");
            }
*/
            drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);
            drive(-frontLeft, -frontRight, -rearLeft, -rearRight);
            telemetry.update();
            idle();


            telemetry.update();
            idle();
        }

    }


    private void drive( double lf, double rf, double lr, double rr1){

        left_f.setPower(lf);
        right_f.setPower(rf);
        left_r.setPower(lr);
        right_r.setPower(rr1);

    }

    private void drop( int p){

        lift_arm.setTargetPosition(p);
        lift_arm.setPower(-0.7);

    }

    private void climb( int o){

        lift_arm.setTargetPosition(o);
        lift_arm.setPower(1.0);

    }


    private void intake(double inP){

        left_in.setPower(-inP);
        right_in.setPower(inP);

    }

    private void extendUp(int u){

        extendUp.setTargetPosition(u);
        extendUp.setPower(0.5);

    }

    private void extendDown(int d){
        extendUp.setTargetPosition(d);
        extendUp.setPower(-0.5);
    }

    private void flipUp(double s){

        flip_up.setPosition(s);

    }

    private void flipDown(){

        flip_up.setPosition(0);

    }

}