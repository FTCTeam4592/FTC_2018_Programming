package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;



@Autonomous(name="AUTONOMOUS", group="Encoder")
//@Disabled
public class CraterAuto extends LinearOpMode {

    /* Declare OpMode members. */

    private ElapsedTime     runtime = new ElapsedTime();

    DcMotor tRight, tLeft, bRight, bLeft, lift_arm, flip_out;




    static final double     COUNTS_PER_MOTOR_REV    = 40.0 ;    // eg: AndyMark NeverRest40 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = .5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = .5;
    static final double     TURN_SPEED              = .5;

    @Override
    public void runOpMode() {
        tLeft = hardwareMap.dcMotor.get("left_front");
        tRight = hardwareMap.dcMotor.get("right_front");
        bRight = hardwareMap.dcMotor.get("right_rear");
        bLeft = hardwareMap.dcMotor.get("left_rear");
        tLeft.setDirection(DcMotor.Direction.FORWARD);
        tRight.setDirection(DcMotor.Direction.REVERSE);
        bRight.setDirection(DcMotor.Direction.REVERSE);
        bLeft.setDirection(DcMotor.Direction.FORWARD);

        lift_arm = hardwareMap.dcMotor.get("lift_arm");
        lift_arm.setDirection(DcMotor.Direction.REVERSE);


        flip_out = hardwareMap.dcMotor.get("flip_Out");
        flip_out.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flip_out.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        //robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //

        //flip_out.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //flip_out.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        tLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        tLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",
                tLeft.getCurrentPosition(),
                tRight.getCurrentPosition(),
                bLeft.getCurrentPosition(),
                bRight.getCurrentPosition());
        telemetry.update();


        lift_arm.setTargetPosition(2500);
        lift_arm.setPower(-0.5);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        dropLift(3150, 0.6);
        sleep(3000);
        driveForward(0.5, 7);
        idle();
        strafeLeft(0.5,36);
        strafeLeft(0.5, 40);
        turnLeft(0.5,27);
        driveForward(0.5,5);
        flip_out.setTargetPosition(525);
        flip_out.setPower(0.2);
        lift_arm.setTargetPosition(-3000);
        lift_arm.setPower(0.5);


        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches1, double leftInches2, double rightInches1,double rightInches2,
                             double timeoutS) {
        int new_tLeftTarget;
        int new_tRightTarget;
        int new_bLeftTarget;
        int new_bRightTarget;
// Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // encoderDrive(drive_Speed, Front Left, Front Right, Rear Left, Rear Right, timeout)
        //encoderDrive(DRIVE_SPEED,  48,  48, 48, 48, 1.0);  // Strafe Right
        //encoderDrive(DRIVE_SPEED, -24, -24, -24, -24, 1.0);  // Strafe Left
        //encoderDrive(DRIVE_SPEED, -12, 12, 12, -12, 1.0); // Turn Right
        //encoderDrive(DRIVE_SPEED, 12, -12, -12, 12, 1.0); //Turn Left
        //encoderDrive(TURN_SPEED,   12, 12, -12, -12, 1.0);  // DO NOT USE, IT RIPS THE ROBOT APART
        //encoderDrive(DRIVE_SPEED, 12, -12, 12, -12, 1.0);//Go BACKWARDS
        //encoderDrive(DRIVE_SPEED, -2, 2, -2, 2, 1.0);//Go Forward
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            new_tLeftTarget = tLeft.getCurrentPosition() + (int)(leftInches1 * COUNTS_PER_INCH);
            new_tRightTarget = tRight.getCurrentPosition() + (int)(rightInches1 * COUNTS_PER_INCH);
            new_bLeftTarget = bLeft.getCurrentPosition() + (int)(leftInches2 * COUNTS_PER_INCH);
            new_bRightTarget = bRight.getCurrentPosition() + (int)(rightInches2 * COUNTS_PER_INCH);
            tLeft.setTargetPosition(new_tLeftTarget);
            tRight.setTargetPosition(new_tRightTarget);
            bLeft.setTargetPosition(new_bLeftTarget);
            bRight.setTargetPosition(new_bRightTarget);

            // Turn On RUN_TO_POSITION
            tLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            tRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            tLeft.setPower(speed);
            tRight.setPower(speed);
            bLeft.setPower(speed);
            bRight.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ( tLeft.isBusy() && tRight.isBusy()  && bLeft.isBusy() && bRight.isBusy()))

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", new_tLeftTarget,  new_tRightTarget, new_bLeftTarget, new_bRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                    tLeft.getCurrentPosition(),
                    tRight.getCurrentPosition(),
                    bLeft.getCurrentPosition(),
                    bRight.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        tLeft.setPower(0);
        tRight.setPower(0);
        bLeft.setPower(0);
        bRight.setPower(0);

        // Turn off RUN_TO_POSITION
        tLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move
    }
    private void strafeLeft(double speed, double distance){
        encoderDrive(speed, -distance, -distance, -distance, -distance, 1.0);
    }
    private void strafeRight(double speed, double distance){
        encoderDrive(speed, distance, distance, distance, distance, 1.0);
    }
    private void driveForward(double speed, double distance){
        encoderDrive(speed, -distance, distance, -distance, distance, 1.0);
    }
    private void driveBack(double speed, double distance){
        encoderDrive(speed, distance, -distance, distance, -distance, 1.0);
    }
    private void turnLeft(double speed, double distance){
        encoderDrive(speed, distance, -distance, -distance, distance, 1.0);
    }
    private void turnRight(double speed, double distance){
        encoderDrive(speed, -distance, distance, distance, -distance, 1.0);
    }


    private void dropLift(int position, double power){

        lift_arm.setTargetPosition(position);
        lift_arm.setPower(power);

    }
}