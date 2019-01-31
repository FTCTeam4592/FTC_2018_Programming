package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public abstract class Robot4592 extends LinearOpMode {

    public DcMotor leftFront, rightFront, leftRear, rightRear, extendUp, extendOut, liftArm, flipOut;
    public Servo flipUp;
    public CRServo Intake;
    public int liftArmHomePosition = 0;
    public int liftArmTargetPosition = -3100;

    static final double     COUNTS_PER_MOTOR_REV    = 1120.0 ;    // eg: AndyMark NeverRest40 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = .5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)/(WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = .6;
    static final double     TURN_SPEED              = .5;
    static final int        TURN_ANGLE              = 65;
    static final int        MOVE_MINERAL            = 25;
    static final int        DISTANCE_TO_LEFT_M      = 45;
    static final int        RIGHT_M_TO_CENTER_M     = 45; //Check this
    static final int        STRAFE_TO_WALLH         = 10;
    static final int        STRAFE_TO_WALLC         = 14;
    static final int        CENTER_M_TO_LEFT_M      = 18; // check this
    static final int        LEFT_M_TO_WALL          = 60; //THIS IS GOOD
    static final int        CENTER_TO_WALL          = CENTER_M_TO_LEFT_M + LEFT_M_TO_WALL;
    static final int        RIGHT_TO_WALL           = RIGHT_M_TO_CENTER_M + CENTER_M_TO_LEFT_M + LEFT_M_TO_WALL;
    static final int        WALL_TO_HOMEH           = 95; //THIS IS GOOD
    static final int        WALL_TO_HOMEC           = 80;
    static final int        HOME_TO_CRATER          = 120; //THIS IS GOOD

    HardwareMap hwMap;

    public ElapsedTime runtime = new ElapsedTime();


    public DistanceSensor rDS;
    public DistanceSensor fDS;
//private Elapsedtime period = new ElapsedTime();

    protected void tele() {

        // Define and Initialize Drive Train Motors

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set to FORWARD if using AndyMark motors
        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        extendUp = hardwareMap.dcMotor.get("extendUp");
        extendOut = hardwareMap.dcMotor.get("extendOut");

        //flipOut = hardwareMap.servo.get("flipOut");
        //flipOut.setPosition(0);
        flipUp = hardwareMap.servo.get("flipUp");

        flipOut = hardwareMap.dcMotor.get("flipOut");
        flipOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flipOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        extendUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extendUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
// Intake
        Intake = hardwareMap.crservo.get("Intake");

// Lift Arm
        liftArm = hardwareMap.dcMotor.get("liftArm");
        liftArm.setDirection(DcMotor.Direction.REVERSE);
        liftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected void auto() {

        // Define and Initialize Drive Train Motors

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        // Lift Arm - To get off the Lander
        liftArm = hardwareMap.dcMotor.get("liftArm");
        liftArm.setDirection(DcMotor.Direction.REVERSE);
        liftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Extend Up and Flip into Lander components

        extendUp = hardwareMap.dcMotor.get("extendUp");
        extendUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flipUp = hardwareMap.servo.get("flipUp");


        flipOut = hardwareMap.dcMotor.get("flipOut");


        // Extend Out - This is not used in Autonomous

        extendOut = hardwareMap.dcMotor.get("extendOut");
        extendOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Intake - This is not used in Autonomous
        Intake = hardwareMap.crservo.get("Intake");

        rDS = hardwareMap.get(DistanceSensor.class, "rearDistanceSensor");
        fDS = hardwareMap.get(DistanceSensor.class, "frontDistanceSensor");

    }

    public void sampleLeftStay(){

        // GOLD Mineral is in the LEFT Position (Closest to the Driver station)
        telemetry.addData("Gold Mineral Position", "Left");

        String gp = "left";

        strafeLeft(DRIVE_SPEED,40); //Strafe towards minerals and away from lander

        driveReverse(DRIVE_SPEED, DISTANCE_TO_LEFT_M); // Drive to The Gold Mineral

        telemetry.addData("going to", gp);
        telemetry.update();

        pushGold();

        strafeLeft(DRIVE_SPEED, 60);


    }

    public void sampleRightStay(){

        telemetry.addData("Gold Mineral Position", "Right");
        String gp = "right";

        strafeLeft(DRIVE_SPEED,40);

        driveForward(DRIVE_SPEED, 20);
        telemetry.addData("going to", gp);
        telemetry.update();

        pushGold();

        strafeLeft(DRIVE_SPEED, 60);


    }

    public void sampleCenterStay(){

        telemetry.addData("Gold Mineral Position", "Center");
        String gp = "center";

        strafeLeft(DRIVE_SPEED,40);
        //sleep(4000); //allow the thing to read the gold position
        driveForward(DRIVE_SPEED, -15);
        telemetry.addData("going to", gp);
        telemetry.update();

        pushGold();

        strafeLeft(DRIVE_SPEED, 40);

    }


    public void sampleLeft(){

        // GOLD Mineral is in the LEFT Position (Closest to the Driver station)
        telemetry.addData("Gold Mineral Position", "Left");

        String gp = "left";

        strafeLeft(DRIVE_SPEED,40); //Strafe towards minerals and away from lander

        driveReverse(DRIVE_SPEED, DISTANCE_TO_LEFT_M); // Drive to The Gold Mineral

        telemetry.addData("going to", gp);
        telemetry.update();

        pushGold();

        //doRestofAutonomous();
        //OR ALL THIS
        driveReverse(DRIVE_SPEED, LEFT_M_TO_WALL); //Drive Forward to the Wall


    }

    public void sampleRight(){

        telemetry.addData("Gold Mineral Position", "Right");
        String gp = "right";

        strafeLeft(DRIVE_SPEED,40);

        driveForward(DRIVE_SPEED, 20);
        telemetry.addData("going to", gp);
        telemetry.update();

        pushGold();

        driveReverse(DRIVE_SPEED, RIGHT_TO_WALL);

    }
    public void sampleCenter(){

        telemetry.addData("Gold Mineral Position", "Center");
        String gp = "center";

        strafeLeft(DRIVE_SPEED,40);
        //sleep(4000); //allow the thing to read the gold position
        driveForward(DRIVE_SPEED, -15);
        telemetry.addData("going to", gp);
        telemetry.update();

        pushGold();

        driveReverse(DRIVE_SPEED, CENTER_TO_WALL);

    }

    public void autoCrater() {

        turnLeft(DRIVE_SPEED, 22.5); //Turn Parallel to Wall

        strafeLeft(0.7,STRAFE_TO_WALLC); //Strafe to wall

        driveReverse(0.75, WALL_TO_HOMEC); //Drive to Home Depot
        //distanceSensorDrive(-0.5, 24);
        telemetry.addData("distanceFrom", rDS.getDistance(DistanceUnit.INCH));
        telemetry.update();
        sleep(5000);


        flipUp.setPosition(0.49);
        extendUp.setTargetPosition(1500); //changed from 2500
        extendUp.setPower(0.4);
        sleep(1250); //changed from 2000
        flipUp.setPosition(.92);
        sleep(1250); //changed from 2000
        flipUp.setPosition(.4);
        extendUp.setTargetPosition(10);
        extendUp.setPower(0.4);

        driveForward(0.9, HOME_TO_CRATER);
        flipOut.setTargetPosition(400);
        flipOut.setPower(0.4);
    }

    public void autoHome() {

        turnRight(0.75, TURN_ANGLE); //Turn Parallel to Wall

        strafeRight(0.6,STRAFE_TO_WALLH); //Strafe to wall

        driveReverse(0.9, WALL_TO_HOMEH); //Drive to Home Depot

        flipUp.setPosition(0.49);
        extendUp.setTargetPosition(1500);
        extendUp.setPower(0.4);
        sleep(2000);
        flipUp.setPosition(.92);
        sleep(2000);
        flipUp.setPosition(.4);
        extendUp.setTargetPosition(10);
        extendUp.setPower(0.4);

        driveForward(1, HOME_TO_CRATER);
        flipOut.setTargetPosition(400);
        flipOut.setPower(0.4);

    }


    public void encoderDrive(double speed, double leftInches1, double leftInches2, double rightInches1,double rightInches2, double timeoutS) {
        int new_tLeftTarget;
        int new_tRightTarget;
        int new_bLeftTarget;
        int new_bRightTarget;

        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // encoderDrive(drive_Speed, Front Left, Front Right, Rear Left, Rear Right, timeout)
        //encoderDrive(DRIVE_SPEED,  48,  48, 48, 48, 1.0);  // Drive Straight
        //encoderDrive(DRIVE_SPEED, -24, -24, -24, -24, 1.0);  // drive reverse
        //encoderDrive(DRIVE_SPEED, 12, -12, 12, -12, 1.0); // Turn Right
        //encoderDrive(DRIVE_SPEED, -12, 12, -12, 12, 1.0); //Turn Left
        //encoderDrive(TURN_SPEED,   12, 12, -12, -12, 1.0);  // DO NOT USE, IT RIPS THE ROBOT APART
        //encoderDrive(DRIVE_SPEED, 12, 12, -12, -12, 1.0);//Strafe Left
        //encoderDrive(DRIVE_SPEED, -2, 2, -2, 2, 1.0);//Go Forward
        // Ensure that the opmode is still active

        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_tLeftTarget = leftFront.getCurrentPosition() + (int) (leftInches1 * COUNTS_PER_INCH);
            new_tRightTarget = rightFront.getCurrentPosition() + (int) (rightInches1 * COUNTS_PER_INCH);
            new_bLeftTarget = leftRear.getCurrentPosition() + (int) (leftInches2 * COUNTS_PER_INCH);
            new_bRightTarget = rightRear.getCurrentPosition() + (int) (rightInches2 * COUNTS_PER_INCH);
            leftFront.setTargetPosition(new_tLeftTarget);
            rightFront.setTargetPosition(new_tRightTarget);
            leftRear.setTargetPosition(new_bLeftTarget);
            rightRear.setTargetPosition(new_bRightTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftFront.setPower(speed);
            rightFront.setPower(speed);
            leftRear.setPower(speed);
            rightRear.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d :%7d :%7d", new_tLeftTarget, new_tRightTarget, new_bLeftTarget, new_bRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d :%7d :%7d",
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),
                        leftRear.getCurrentPosition(),
                        rightRear.getCurrentPosition());
                telemetry.update();

            }

            // Stop all motion;
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void strafeLeft(double speed, double distance){
        encoderDrive(speed, -distance, distance, -distance, distance, 3.0);
    }
    public void strafeRight(double speed, double distance){
        encoderDrive(speed, distance, -distance, distance, -distance, 3.0);
    }
    public void driveForward(double speed, double distance){
        encoderDrive(speed, distance, distance, distance, distance, 3.0);
    }
    public void driveReverse(double speed, double distance){
        encoderDrive(speed, -distance, -distance, -distance, -distance, 4.0);
    }
    public void turnLeft(double speed, double distance){
        encoderDrive(speed, -distance, -distance, distance, distance, 3.0); //45 is 90 degrees
    }
    public void turnRight(double speed, double distance){
        encoderDrive(speed, distance, distance, -distance, -distance, 3.0);
    }

    private void pushGold() {
        strafeLeft(0.5,MOVE_MINERAL);  //Shift the mineral
        strafeRight(0.6,MOVE_MINERAL); //Move Back
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