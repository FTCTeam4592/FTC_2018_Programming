package org.firstinspires.ftc.teamcode.tele;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot4592;

@TeleOp(name = "here comes the sun")


public class whatIsLove extends Robot4592 {

    @Override
    public void runOpMode(){

        shalama();
        double originalFlipPos = flipOut.getPosition();
        flipOut.setPosition(150);

        waitForStart();

        while (opModeIsActive()) {


            //drive
            double leftyPower;
            double rightyPower;

           /* if(Math.abs(gamepad1.right_stick_y)>Math.abs(gamepad1.right_stick_x)){
                leftyPower = gamepad1.right_stick_y;
                rightyPower = gamepad1.right_stick_y;
                arcade(leftyPower, rightyPower);

            }*/

            if(gamepad1.right_stick_x==0 && gamepad1.right_stick_y==0) {
                float movex = gamepad1.left_stick_y;
                float movey = gamepad1.left_stick_x;


                // roll: right_stick_x ranges from -1 to 1, where -1 is full counterclockwise, and
                // 1 is full clockwise

                float roll = gamepad1.left_trigger;
                float rolr = gamepad1.right_trigger;

                // clip the right/left values so that the values never exceed +/- 1
                movex = Range.clip(movex, -1, 1);
                movey = Range.clip(movey, -1, 1);
                roll = Range.clip((roll * 2), -1, 1);
                rolr = Range.clip((rolr * 2), -1, 1);

                // scale the joystick value to make it easier to control
                // the robot more precisely at slower speeds.
                movex = (float) scaleInput(movex);
                movey = (float) scaleInput(movey);
                roll = (float) scaleInput(roll);
                rolr = (float) scaleInput(rolr);

                // write the values to the motors
                leftFront.setPower(Range.clip(-movex + movey + roll - rolr, -1, 1));
                leftRear.setPower(Range.clip(movex - movey + roll - rolr, -1, 1));
                rightFront.setPower(Range.clip(-movex - movey - roll + rolr, -1, 1));
                rightRear.setPower(Range.clip(movex + movey - roll + rolr, -1, 1));
            }
            else{
                shalama();
                leftyPower = gamepad1.right_stick_y;
                rightyPower = gamepad1.right_stick_y;
                arcade(leftyPower,rightyPower);
            }
           // }
           /* else{
                leftyPower = -1 * gamepad1.right_stick_y;
                rightyPower = gamepad1.right_stick_y;
                arcade(leftyPower, rightyPower);

            }
*/
  //         drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);

            //Extend Up
            int extend_up_position = extendUp.getCurrentPosition();
            int extend_up_top  = 1000;

            if (gamepad2.b && liftArm.getCurrentPosition() >= -50) {
                liftArm.setTargetPosition(-3350);
                liftArm.setPower(0.5);
            } else if (gamepad2.b && liftArm.getCurrentPosition() < -50) {
                liftArm.setTargetPosition(0);
                liftArm.setPower(-0.5);
            }

            if (gamepad2.x && (extendUp.getCurrentPosition() <= 550)) {
                extendUp.setTargetPosition(8000);
                extendUp.setPower(1);
                extendUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //extendUp.setPower(0);
            } else if (gamepad2.x && (extendUp.getCurrentPosition() > 550)) {
                extendUp.setTargetPosition(0);
                extendUp.setPower(-1);
                extendUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //extendUp.setPower(0);
            }

            double ext;

            if(gamepad2.right_bumper && (extendOut.getCurrentPosition() <= 4700)){
                ext = -1;
            }
            else if(gamepad2.left_bumper && (extendOut.getCurrentPosition() > 0)){
                ext = 1;
            }
            else{
                ext = 0;
            }
            extendOut.setPower(ext);

            if(gamepad2.dpad_up){
                flipOut.setPosition(550);
                //flipOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                //flipOut.setPower(0);
            }
            if(gamepad2.dpad_down){

                flipOut.setPosition(150);
                //flipOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

            if(gamepad2.y && (flipUp.getPosition() < 0.625)){
                flipUp.setPosition(0.9);
            }
            else if(gamepad2.y && (flipUp.getPosition() > 0.625)){
                flipUp.setPosition(0.45);
            }

            if(Math.abs(gamepad2.left_stick_y)>0){
                Intake.setPower(0.75);
            }
            else{
                Intake.setPower(0);
            }

            telemetry.addData("lift", liftArm.getCurrentPosition());
            telemetry.addData("extend up", extendUp.getCurrentPosition());
            telemetry.addData("extend out", flipOut.getPosition());
            telemetry.addData("flip out", extendOut.getCurrentPosition());
            telemetry.addData("flip up", flipUp.getPosition());

            telemetry.update();


        }
    }

    private void drive( double lf, double rf, double lr, double rr1){

        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftRear.setPower(lr);
        rightRear.setPower(rr1);

    }

    private void arcade(double left, double right){

        leftFront.setPower(left);
        leftRear.setPower(left);
        rightFront.setPower(right);
        rightRear.setPower(right);

    }

    /*private void turn(double left, double right){
        leftFront.setPower(left);
        leftRear.setPower();
        rightFront.setPower();
    }
*/
}
