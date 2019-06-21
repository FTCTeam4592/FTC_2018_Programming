package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot4592;
import org.firstinspires.ftc.teamcode.RobotBase;


/**
 * Created by user on 12/9/17.
 */


@TeleOp(name = "Arcade Drive Test")
@Disabled
public class ArcadeDriveTest extends Robot4592 {

    @Override
    public void runOpMode() throws InterruptedException {
        tele();

        waitForStart();


        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        final double originalFlipPos = flipOut.getCurrentPosition();


        while (opModeIsActive()) {
            // update

            final int drop_position = 0;
            final int original_position = 3000;
            final int extendUp_Raised = 4000;
            final int extendUp_Lowered = 0;
            final int original_out = extendOut.getCurrentPosition();
            final int original_up = extendUp.getCurrentPosition();
            final double originial_flip_up = flipUp.getPosition();

            //strafe
            double leftFront_Power = -1.5 * Math.cos(gamepad1.left_stick_y) * Math.sin(gamepad1.left_stick_x);
            double rightFront_Power = -leftFront_Power;
            double leftRear_Power = -leftFront_Power;
            double rightRear_Power = leftFront_Power;

            //drive
            double leftPower;
            double rightPower;

            if(Math.abs(gamepad1.right_stick_y)>Math.abs(gamepad1.right_stick_x)){
                leftPower = gamepad1.right_stick_y;
                rightPower = gamepad1.right_stick_y;
            }
            else{
                leftPower = -gamepad1.right_stick_x;
                rightPower = gamepad2.right_stick_x;
            }

            drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);
            arcade(leftPower, rightPower);


            //lift arm
            if (gamepad2.x && liftArm.getCurrentPosition()>= 500) {
                drop(0); //when x is pressed, the lift arm will go down
            }
            if (gamepad2.x && liftArm.getCurrentPosition() < 500) {
                climb(3200); //when x is pressed, teh lift arm will go up
            }


            //extend vertical
            if (gamepad2.b && extendUp.getCurrentPosition() <= 1500) {
                extendUp(extendUp_Raised);
            } else if (gamepad2.b && extendUp.getCurrentPosition() >= 1500) {
                extendUp(extendUp_Lowered);
            }

            //extend horizontal
            double ext;

            if(gamepad2.right_bumper){
                ext = 1;
            }
            else if(gamepad2.left_bumper){
                ext = -1;
            }
            else{
                ext = 0;
            }
                extendOut.setPower(ext);



            //flip up
            if (gamepad2.dpad_up){

                flipUp(0.6);
            }
            else if (gamepad2.dpad_down){
                flipUp(0.1);
            }


            Intake.setPower(0.85*gamepad2.left_stick_y);
            telemetry.addData("leftfrontpower", leftFront_Power);
            telemetry.addData("leftrearpower", leftRear_Power);
            telemetry.addData("rightfrontpower", rightFront_Power);
            telemetry.addData("rightrearpower", rightRear_Power);
            telemetry.addData("lift position", liftArm.getCurrentPosition());
            telemetry.addData("extension position", extendOut.getCurrentPosition());
            telemetry.addData("extend up", extendUp.getCurrentPosition());
            telemetry.addData("flipup", flipUp.getPosition());
            telemetry.addData("flip out", flipOut.getCurrentPosition());
            /*
            if (isGold) {
                telemetry.addLine("IT WORKS BABYŸŸŸŸŸŸ;;;;;;;;;;;;YY");
            } else {
                telemetry.addLine("THIS AIN'T IT");
            }
*/

            telemetry.update();
            idle();


            telemetry.update();
            idle();
        }

    }


    private void drop( int p){

        liftArm.setTargetPosition(p);
        liftArm.setPower(-0.7);

    }

    private void climb( int o){

        liftArm.setTargetPosition(o);
        liftArm.setPower(1.0);

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

        flipUp.setPosition(s);

    }

    private void flipDown(){

        flipUp.setPosition(0);

    }

    private void arcade(double left, double right){

        leftFront.setPower(left);
        leftRear.setPower(left);
        rightFront.setPower(right);
        rightRear.setPower(right);

    }

}