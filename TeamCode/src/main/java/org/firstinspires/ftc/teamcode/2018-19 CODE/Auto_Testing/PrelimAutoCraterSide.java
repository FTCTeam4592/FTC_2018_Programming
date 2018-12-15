package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.TensorFlow;

import java.util.List;

@Autonomous(name = "GREENBEANMONEYMACHINE")
public class PrelimAutoCraterSide extends RobotBase {

    public TFObjectDetector tfod;

    @Override
    public void runOpMode() throws InterruptedException {

        setup();

        left_f.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_f.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Reset Drive Motors

        left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        left_f.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_f.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //init lift arm to lowest position
        lift_arm.setTargetPosition(10);
        lift_arm.setPower(-1);

        waitForStart();
        drop(3300);
        sleep(4000);
        setPosition(1000);

        //right_f.setTargetPosition(1200);
        //left_f.setTargetPosition(1200);
        //right_r.setTargetPosition(1200);
        //left_f.setTargetPosition(1200);
        right_r.setPower(1);
        left_r.setPower(1);
        right_f.setPower(1);
        left_f.setPower(1);
        //sleep(6000);
        //strafeLeft(1500);
        //sleep(1000);
        //right_r.setPower(0);
        //left_r.setPower(0);

           /*
            sleep(2000);
            driveForward(300);//fix number with testing
            resetMotors();
            //climb(100);
            sleep(3000);
            strafeLeft(1000);//fix number with testing
            resetMotors();
*/
            telemetry.addData("lift position", lift_arm.getCurrentPosition());
            telemetry.addData("fl", left_f.getCurrentPosition());
            telemetry.addData("fr", right_f.getCurrentPosition());
            telemetry.addData("rl", left_r.getCurrentPosition());
            telemetry.addData("rr", right_r.getCurrentPosition());
            telemetry.update();
            idle();


/*
        boolean isGold = false;

        TensorFlow tf = new TensorFlow();
        tf.runOpMode();
        if (tfod != null) {
            tfod.activate();
        }

        //middle
        List<Recognition> updatedRecognitions = tf.tfod.getUpdatedRecognitions();
        for (Recognition recognition : updatedRecognitions) {
            if (recognition.getLabel().equals("Gold Mineral")) {
                isGold = true;
            } else {
                isGold = false;
            }
        }
        if(isGold == true){
            strafe(1000);
        }
        else{
            driveToPosition(-1000);
            for (Recognition recognition : updatedRecognitions) {
                if (recognition.getLabel().equals("Gold Mineral")) {
                    isGold = true;
                } else {
                    isGold = false;
                }
                if(isGold == true){
                    strafe(1000);
                }
                else{
                    driveToPosition(2000);
                    strafe(1000);
                }
            }
        }

*/
        }

    private void turn(double a){

        double angle = (a/360)*((18*Math.PI)/(Math.PI*1.5*1.5)); //find rotations required to turn this angle
        int turnTicks = (int) (angle*255);
        left_f.setTargetPosition(turnTicks);
        left_r.setTargetPosition(turnTicks);
        right_r.setTargetPosition(turnTicks);
        right_f.setTargetPosition(turnTicks);
        left_f.setPower(0.7);
        left_r.setPower(0.7);
        right_r.setPower(0.7);
        right_f.setPower(0.7);

    }

    private void strafeLeft(int s){
        left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_f.setTargetPosition(s);
        left_f.setPower(0.7);
        right_f.setTargetPosition(-s);
        right_f.setPower(0.7);
        left_r.setTargetPosition(s);
        left_r.setPower(0.7);
        right_r.setTargetPosition(-s);
        right_r.setPower(0.7);

    }

    private void driveToPosition( int d){
        left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_f.setTargetPosition(d);
        left_f.setPower(-0.7);
        right_f.setTargetPosition(d);
        right_f.setPower(-0.7);
        left_r.setTargetPosition(d);
        left_r.setPower(-0.7);
        right_r.setTargetPosition(d);
        right_r.setPower(-0.7);

    }
    private void driveForward( int d){
       // left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ight_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_f.setTargetPosition(d);
        left_f.setPower(0.7);
        right_f.setTargetPosition(d);
        right_f.setPower(-0.7);
        left_r.setTargetPosition(d);
        left_r.setPower(0.7);
        right_r.setTargetPosition(d);
        right_r.setPower(-0.7);

    }

    private void driveBackwards( int d){
        left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_f.setTargetPosition(d);
        left_f.setPower(-0.7);
        right_f.setTargetPosition(d);
        right_f.setPower(0.7);
        left_r.setTargetPosition(d);
        left_r.setPower(-0.7);
        right_r.setTargetPosition(d);
        right_r.setPower(0.7);

    }

    private void drop(int p){

        lift_arm.setTargetPosition(p);
        lift_arm.setPower(0.3);

    }

    private void climb(int o){

        lift_arm.setTargetPosition(o);
        lift_arm.setPower(1.0);

    }

    private void resetMotors() {
        left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}