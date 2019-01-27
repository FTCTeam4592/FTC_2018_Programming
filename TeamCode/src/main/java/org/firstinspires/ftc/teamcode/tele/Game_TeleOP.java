package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot4592;



@TeleOp(name = "Game TeleOp", group="TeleOp Tournament")
public class Game_TeleOP extends Robot4592 {

        @Override
        public void runOpMode() {

            // Run TeleOp Initialization
            tele();

           // liftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //flipOut.setPosition(0);

            flipUp.setPosition(0.49);


            waitForStart();

            while (opModeIsActive()) {


                //strafe right
                double leftFront_Power = Range.clip(-1 * gamepad1.left_stick_x, -1, 1);
                double rightFront_Power = Range.clip(leftFront_Power, -1, 1);
                double leftRear_Power = Range.clip(-leftFront_Power, -1, 1);
                double rightRear_Power = Range.clip(-leftFront_Power, -1, 1);

                leftFront_Power = (float) scaleInput(leftFront_Power);
                rightFront_Power = (float) scaleInput(rightFront_Power);
                leftRear_Power = (float) scaleInput(leftRear_Power);
                rightRear_Power = (float) scaleInput(rightRear_Power);

                //drive
                double frontRight = Range.clip(-gamepad1.right_stick_y, -1, 1);
                double rearRight = Range.clip(-gamepad1.right_stick_y, -1, 1);
                double frontLeft = Range.clip(gamepad1.left_stick_y, -1, 1);
                double rearLeft = Range.clip(gamepad1.left_stick_y, -1, 1);

                frontRight = (float) scaleInput(frontRight);
                rearRight = (float) scaleInput(rearRight);
                frontLeft = (float) scaleInput(frontLeft);
                rearLeft = (float) scaleInput(rearLeft);


                int current;

                if(Math.abs(gamepad1.left_stick_x * gamepad1.left_stick_y)>0 && ((gamepad1.right_stick_x+gamepad1.right_stick_y)==0)) {
                    //strafe
                    drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);
                }
                else {
                    //drive
                    drive(-frontLeft, frontRight, -rearLeft, rearRight);
                }
                int liftArmPosition = 0;
                //Extend Up
                int extend_up_position = extendUp.getCurrentPosition();
                int extend_up_top = 1000;

                // Gamepad B toggles the Lift Arm Position from bottom (0) to the top (-3100)
                if (gamepad2.b && liftArm.getCurrentPosition()>-200) {
                        liftArm.setTargetPosition(-3300);
                        liftArm.setPower(0.5);
                    }
                    if(gamepad2.b && liftArm.getCurrentPosition()<-200){
                        liftArm.setTargetPosition(0);
                        liftArm.setPower(1);

                    }

                if (gamepad2.x && (extendUp.getCurrentPosition() <= 550)) {
                    extendUp.setTargetPosition(7750);
                    extendUp.setPower(1);
                    //extendUp.setPower(0);
                } else if (gamepad2.x && (extendUp.getCurrentPosition() > 550)) {
                    extendUp.setTargetPosition(0);
                    extendUp.setPower(-1);
                    //extendUp.setPower(0);
                }

                if(gamepad1.right_bumper){
                    strafeRight(0.75, 15);
                }
                if(gamepad1.left_bumper){
                    driveReverse(0.75, 10);
                }

                double ext;

                if (gamepad2.right_bumper) { // && extendOut.getCurrentPosition()<5000  add this
                    ext = 1;
                } else if (gamepad2.left_bumper) { // && extendOut.getCurrentPosition()>5000  add this
                    ext = -1;
                } else {
                    ext = 0;
                }

                extendOut.setPower(ext);

                telemetry.addData("test", flipOut.getDirection());
                //flipOut.setPosition(0);
                telemetry.addData("current pos", flipOut.getCurrentPosition());
                //
                //flipOut.setDirection(Servo.Direction.REVERSE);

                /*
                if (gamepad2.dpad_down && (flipOut.getPosition() < 0.2)) {
                    // Flip out to the mat

                    telemetry.addData("test", flipOut.getDirection());
                    telemetry.addData("current pos", flipOut.getPosition());
                    telemetry.update();
                    flipOut.setPosition(0.8);
                } else if (gamepad2.dpad_up && (flipOut.getPosition() >0.2)) {

                   // flipOut.setDirection(Servo.Direction.FORWARD);
                    telemetry.addData("current pos", flipOut.getPosition());
                    telemetry.update();
                    flipOut.setPosition(0.4);
                }

                if (gamepad2.dpad_left){
                    telemetry.addData("current pos", flipOut.getPosition());
                    flipOut.setPosition(0.47);
                }
                */

                if(gamepad2.dpad_down){
                    flipOut.setTargetPosition(555);
                    flipOut.setPower(0.4);
                }
                if(flipOut.getCurrentPosition()<=15){
                    flipOut.setPower(0);
                }
                if(gamepad2.dpad_up){

                    flipOut.setTargetPosition(0);
                    flipOut.setPower(0.4);

                }
                if(gamepad2.dpad_left){
                    flipOut.setTargetPosition(150);
                    flipOut.setPower(0.4);
                }

                if (gamepad2.y && (flipUp.getPosition() < 0.55) && (extendUp.getCurrentPosition()>550)) {
                    flipUp.setPosition(0.92);
                } else if (gamepad2.y && (flipUp.getPosition() > 0.55)) {
                    flipUp.setPosition(0.49);
                }

                // If GamePad 2 button A is pressed, turn on Intake Motor
                /*
                if (gamepad2.a) {
                    Intake.setPower(0.4);
                    telemetry.addData("intake", Intake.getPower());
                    telemetry.update();

                }
                */

                Intake.setPower(-1*((gamepad2.left_stick_y)*0.85));



                telemetry.addData("lift", liftArm.getCurrentPosition());
                telemetry.addData("extend up", extendUp.getCurrentPosition());
                telemetry.addData("extend out", extendOut.getCurrentPosition());
                telemetry.addData("flip up", flipUp.getPosition());
                telemetry.addData("rightfrontpow", rightFront.getPower());
                telemetry.addData("rightpow", rightRear.getPower());
                telemetry.addData("current", flipOut.getCurrentPosition());
                telemetry.addData("intake", Intake.getPower());
                telemetry.update();

            }





        }

        private void arcade(double left, double right){

            double rightFrontPower = right * -1.0;
            double leftFrontPower = left * -1.0;

            telemetry.addData("rightfrontpow", rightFrontPower);
            telemetry.addData("rightpow", right);
            leftFront.setPower(leftFrontPower);
            leftRear.setPower(leftFrontPower);
            rightFront.setPower(rightFrontPower);
            rightRear.setPower(rightFrontPower);
            telemetry.update();
        }

        private void tank(double left, double right){

            leftFront.setPower(left);
            leftRear.setPower(left);
            rightFront.setPower(right);
            rightRear.setPower(right);


        }

        private void drive( double lf, double rf, double lr, double rr1){

        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftRear.setPower(lr);
        rightRear.setPower(rr1);

        }

        private void intake(double inP){
            Intake.setPower(inP);
        }
    /*private void turn(double left, double right){
        leftFront.setPower(left);
        leftRear.setPower();
        rightFront.setPower();
    }
*/
    }


