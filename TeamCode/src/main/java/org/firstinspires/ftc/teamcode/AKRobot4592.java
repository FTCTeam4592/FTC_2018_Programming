package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public abstract class AKRobot4592 extends LinearOpMode {

    protected ElapsedTime runtime = new ElapsedTime();
    protected DcMotor leftFront, leftRear, rightFront, rightRear, liftArm, flipOut, extendUp, extendOut;
    protected CRServo intake;
    protected Servo flip_up;
    protected BNO055IMU imu;
    double Power = 0;
    int Lift_bottom = 0;
    int Lift_top = 3200;
    int extend_up_min = 0;
    int extend_up_max = 3350;
    double flip_up_min = 0.2;
    double flip_up_max = 0.7;
    int extend_out_home = 0;
    int extend_out_horizontal = 0;
    int extend_out_down = 0;

    Orientation orientation;
    Acceleration acceleration;

    // protected HardwareMap hwMap;
    protected void setup() {

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        liftArm = hardwareMap.dcMotor.get("liftArm");
        flipOut = hardwareMap.dcMotor.get("flipOut");
        extendUp = hardwareMap.dcMotor.get("extendUp");
        extendOut = hardwareMap.dcMotor.get("extendOut");

        intake = hardwareMap.crservo.get("Intake");
        flip_up = hardwareMap.servo.get("flipUp");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setPower(0.0);
        leftRear.setPower(0.0);
        rightRear.setPower(0.0);
        rightFront.setPower(0.0);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}



