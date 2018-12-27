package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class Robot4592 extends LinearOpMode {

    public DcMotor leftFront, rightFront, leftRear, rightRear, extendUp, extendOut, liftArm, flipOut;
    public Servo flipUp;
    public CRServo Intake;

    HardwareMap hwMap;

    //private Elapsedtime period = new ElapsedTime();

    protected void shalama() {
        // Define and Initialize Motors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        liftArm = hardwareMap.dcMotor.get("liftArm");
        extendUp = hardwareMap.dcMotor.get("extendUp");
        extendOut = hardwareMap.dcMotor.get("extendOut");
        flipOut = hardwareMap.dcMotor.get("flipOut");
        flipUp = hardwareMap.servo.get("flipUp");
        Intake = hardwareMap.crservo.get("Intake");

        leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        //flipOut.setDirection(DcMotor.Direction.FORWARD);

        // Set to FORWARD if using AndyMark motors
        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);


        // Use RUN_USING_ENCODERS if encoders are installed.

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extendUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        liftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }

    protected void shalamaDrive() {
        // Define and Initialize Motors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        liftArm = hardwareMap.dcMotor.get("liftArm");
        extendUp = hardwareMap.dcMotor.get("extendUp");
        extendOut = hardwareMap.dcMotor.get("extendOut");
        flipOut = hardwareMap.dcMotor.get("flipOut");
        flipUp = hardwareMap.servo.get("flipUp");
        Intake = hardwareMap.crservo.get("Intake");

        leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightRear.setDirection(DcMotor.Direction.REVERSE);

        //flipOut.setDirection(DcMotor.Direction.FORWARD);

        // Set to FORWARD if using AndyMark motors
        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        // Use RUN_USING_ENCODERS if encoders are installed.
        extendUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);





    }


    public double scaleInput(double dVal)  {
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