package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wardp on 1/13/2018.
 */

public abstract class RobotBase extends LinearOpMode {

    public boolean isGold;

    protected DcMotor left_f, left_r, right_f, right_r, lift_arm, flip_out, extendUp, extendOut;

    protected CRServo left_in, right_in;

    protected Servo flip_up;

    protected void setup() {

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        /* Drivetrain setup block */
        left_f = hardwareMap.dcMotor.get("left_front");
        left_r = hardwareMap.dcMotor.get("left_rear");
        right_f = hardwareMap.dcMotor.get("right_front");
        right_r = hardwareMap.dcMotor.get("right_rear");

        left_f.setDirection(DcMotor.Direction.REVERSE);
        left_r.setDirection(DcMotor.Direction.FORWARD);
        right_f.setDirection(DcMotor.Direction.REVERSE);
        right_r.setDirection(DcMotor.Direction.FORWARD);

        //left_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //left_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // right_f.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //right_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /* lift setup block */
        lift_arm = hardwareMap.dcMotor.get("lift_arm");
        lift_arm.setDirection(DcMotor.Direction.REVERSE);
        //lift_arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //extension arms setup block

        extendUp = hardwareMap.dcMotor.get("extend_Up");
        extendOut = hardwareMap.dcMotor.get("extend_Out");
        extendOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        extendOut.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //extendOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //extendOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //flip setup block

        flip_out = hardwareMap.dcMotor.get("flip_Out");
        flip_up = hardwareMap.servo.get("flip_Up");

        //flip_out.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flip_out.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flip_out.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //intake setup block
        left_in = hardwareMap.crservo.get("left_in");
        right_in = hardwareMap.crservo.get("right_in");




    }

    /**
     * Replaces opModeIsActive, with no idle() call.
     * @return Whether the opmode is active and should continue looping
     */
    public boolean shouldContinueRunning() {
        return (!this.isStopRequested() && this.isStarted());
    }

    protected void setPosition(int position) {
        left_f.setTargetPosition(position);
        left_r.setTargetPosition(position);
        right_f.setTargetPosition(-1*position);
        right_r.setTargetPosition(-1*position);
    }

    public void setDrivetrainMode(DcMotor.RunMode mode) {
        left_f.setMode(mode);
        left_r.setMode(mode);
        right_f.setMode(mode);
        right_r.setMode(mode);
    }

    public void setDrivetrainPower(double left, double right) {
        setDrivetrainPower((float)left, (float)right);
    }

    public void setDrivetrainPower(float left, float right) {
        left_f.setPower(left);
        left_r.setPower(left);
        right_f.setPower(right);
        right_r.setPower(right);
    }
}