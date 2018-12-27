package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot4592;



@TeleOp(name = "Game TeleOp")
public class Game_TeleOP extends Robot4592 {

        @Override
        public void runOpMode() {

            shalama();
            //int originalFlipPos = flipOut.getCurrentPosition();
           // flipOut.setTargetPosition(150);

            waitForStart();

            while (opModeIsActive()) {


                //strafe
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


                if (Math.abs(gamepad1.left_stick_x * gamepad1.left_stick_y) > 0 && ((gamepad1.right_stick_x + gamepad1.right_stick_y) == 0)) {
                    drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);
                } else {
                    drive(-frontLeft, frontRight, -rearLeft, rearRight);
                }

                //Extend Up
                int extend_up_position = extendUp.getCurrentPosition();
                int extend_up_top = 1000;

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

                if (gamepad2.right_bumper && (extendOut.getCurrentPosition() <= 4700)) {
                    ext = -1;
                } else if (gamepad2.left_bumper && (extendOut.getCurrentPosition() > 0)) {
                    ext = 1;
                } else {
                    ext = 0;
                }
                extendOut.setPower(ext);

                if (gamepad2.dpad_down && (extendOut.getCurrentPosition() > -20)) {
                    flipOut.setTargetPosition(-500);
                    flipOut.setPower(0.2);
                    flipOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //telemetry.addData("target", flipOut.getTargetPosition());
                    telemetry.addData("current", extendOut.getCurrentPosition());
                    telemetry.update();
                } else if (gamepad2.dpad_up && (extendOut.getCurrentPosition() < -400)) {
                    flipOut.setTargetPosition(0);
                    flipOut.setPower(0.4);
                    flipOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //telemetry.addData("target1", flipOut.getTargetPosition());
                    //telemetry.addData("current1", flipOut.getCurrentPosition());
                    //telemetry.update();
                }

            }


            if (gamepad2.y && (flipUp.getPosition() < 0.625)) {
                flipUp.setPosition(0.9);
            } else if (gamepad2.y && (flipUp.getPosition() > 0.625)) {
                flipUp.setPosition(0.45);
            }

            telemetry.addData("lift", liftArm.getCurrentPosition());
            telemetry.addData("extend up", extendUp.getCurrentPosition());
            telemetry.addData("extend out", flipOut.getCurrentPosition());
            telemetry.addData("flip out", extendOut.getCurrentPosition());
            telemetry.addData("flip up", flipUp.getPosition());
            telemetry.addData("rightfrontpow", rightFront.getPower());
            telemetry.addData("rightpow", rightRear.getPower());
            telemetry.addData("targ", flipOut.getTargetPosition());
            telemetry.update();


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


