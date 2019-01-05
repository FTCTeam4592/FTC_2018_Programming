package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.vision.AKObjectDetection;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.List;


@Autonomous(name="AUTONOMOUS_2019_05", group="Encoder")
//@Disabled


public class TesterAutonomous extends Robot4592 {

    /* Declare OpMode members. */

    private ElapsedTime     runtime = new ElapsedTime();

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private String gp = "";

    private DistanceSensor rDS;



    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AR28rTb/////AAAAGdmyxbWoaEUfpgbw+HH9dAR6pd2GE0zLPpsObm9c3iyHvFxLGIrvMEkriYpEMFXybIOF1ng9sKMrJr1He8aXsUQ+7zxItkmGs69z3vTyLgRRD0eUrIJXViYt+tk6IzPYE+4Z9v5hWKteebG3TfzVmT/H/kg6vMLzQblDYNcz4JJZYrCq2axfHBhrDp6ljJQv0esY5DacKVrFLn1H6Jkaxe0vuZFsOveYpTzRdY4v4UuXqEwUxz+NdM/++RZncWkbftEpcLaf1tMFkTZBCOdQ5Tx+HXoT1bpepy1hHF1E6+cwxiUxZAx1ZxbsH5IJ+TfVtk2GjGD1R9CqSqvDE+8fWY12BOZP3PTSdHLaVgCmw/hq";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;



    static final double     COUNTS_PER_MOTOR_REV    = 1120.0 ;    // eg: AndyMark NeverRest40 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = .5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)/(WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = .5;
    static final double     TURN_SPEED              = .5;
   // MasterVision vision;
    //SampleRandomizedPositions goldPosition;
   AKObjectDetection detected = new AKObjectDetection();


    @Override
    public void runOpMode() {

        rDS = hardwareMap.get(DistanceSensor.class,"rearDistanceSensor");


        auto();

      /*  VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;// recommended camera direction
        parameters.vuforiaLicenseKey = "ARJCp6T/////AAABmRttT+LHV03viaux59tDgwQAcMq1HFTvZNKn5yFhA+l2VltLOSTPHHtHahoM9BTEmQKSs31iPWOjUw6PquYvKi/swRXOSNvJdHzqT7NvkcAiS8tHg/oV7YYATIbGItnLWdKdAVxxCdyTEsAhpNjSPB13B9F9cN6k4tYr38faOz51bbINpcKd6jivqJDwatyuaU2r9F5eSERe2GrzZfSIqUCZdW3tDIhXCgsJ1U4AS6QdYspg0yoG88VsxAZHNZvEl2Ldc7tenqS2MBLBSORv8uQisk6wgqJSlv4oOnoQoMd9p72+cAV2HUO5I1uynCeR/ON8oSMxfmaa4spc51p8Ek7EK7mtaEy+SgkSDC/EYSQ8";

        vision = new MasterVision(parameters, hardwareMap, false, MasterVision.TFLiteAlgorithm.INFER_LEFT);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        //robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.addData("lift Arm [position", liftArm.getCurrentPosition());
        telemetry.addData("lift Arm [position", liftArm.getCurrentPosition());
        telemetry.update();
        //flip_out.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //flip_out.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        idle();


        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",
                leftFront.getCurrentPosition(),
                rightFront.getCurrentPosition(),
                leftRear.getCurrentPosition(),
                rightRear.getCurrentPosition());
        telemetry.update();


        //liftArm.setTargetPosition(0);
        //.addData("hi","hi");
        //.update();
        //liftArm.setPower(-0.5);

        // Wait for the game to start (driver presses PLAY)
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("status","waiting for start command...");
            telemetry.update();
        }

        //vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

       // goldPosition = vision.getTfLite().getLastKnownSampleOrder();

       // telemetry.addData("goldPosition was", goldPosition);// giving feedback
        //Drop Robot from the Lander
        dropLift(-3000, 0.6);
        sleep(3000);
        int liftArmCurrentPosition = liftArm.getCurrentPosition() ;

        liftArm.setPower(0);

        //To release the hook from the Lander
        driveForward(0.5, 13);
        sleep(1000);
        dropLift(0,0.6);
        action();

       // sleep(4000); //allow the thing to read the gold position
        //strafeLeft(0.5,40);

       /* if(gp.equals("right")) {
            driveForward(0.5, 9);
        }
        else if(gp.equals("center")){
            driveForward(0.5, -4);
        }
        else if(gp.equals("left")){
            driveForward(0.5, -20);
        }
*/


        /*vision.enable();
        sleep(2000);
        vision.disable();
        switch (goldPosition){ // using for things in the autonomous program
            case LEFT:
                telemetry.addLine("going to the left");
                driveForward(0.5, 12);
                turnLeft(0.5, 12);
                break;
            case CENTER:
                telemetry.addLine("going straight");
                turnLeft(0.5, 12);
                break;
            case RIGHT:
                telemetry.addLine("going to the right");
                driveBack(0.5, 12);
                turnLeft(05., 12);
                break;
            case UNKNOWN:
                telemetry.addLine("staying put");
                break;
        }*/
        telemetry.update();
       /*
        turnLeft(0.5,27);
        driveForward(0.5,5);
        flipOut.setPosition(.525);                                          //ADJUST THIS
        liftArm.setTargetPosition(-3000);
        liftArm.setPower(0.5);
*/
       dropLift(0, 0.5);
        telemetry.addData("hello","hello");
        telemetry.update();
        sleep(3000);     // pause for servos to move

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

               // TensorFlow tflow = new TensorFlow();

                //telemetry.addData("Position", tflow.getPosition());
               // telemetry.update();

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
    private void strafeLeft(double speed, double distance){
        encoderDrive(speed, -distance, distance, -distance, distance, 2.5);
    }
    private void strafeRight(double speed, double distance){
        encoderDrive(speed, distance, distance, distance, distance, 3.0);
    }
    private void driveForward(double speed, double distance){
        encoderDrive(speed, distance, distance, distance, distance, 3.0);
    }
    private void driveBack(double speed, double distance){
        encoderDrive(speed, -distance, -distance, -distance, -distance, 3.0);
    }
    private void turnLeft(double speed, double distance){
        encoderDrive(speed, -distance, -distance, distance, distance, 3.0); //45 is 90 degrees
    }
    private void turnRight(double speed, double distance){
        encoderDrive(speed, -distance, distance, distance, -distance, 3.0);
    }
    private void driveWithSensor(double speed, double desiredDistance) {
        while (rDS.getDistance(DistanceUnit.INCH) > desiredDistance + 1) {
            leftFront.setPower(speed);
            rightFront.setPower(speed);
            leftRear.setPower(speed);
            rightRear.setPower(speed);

        }
    }

    public void action() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() > 0) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 || goldMineralX != -1 && silverMineral2X != -1 || silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    gp = "left";
                                    strafeLeft(0.5,40);
                                    //sleep(4000); //allow the thing to read the gold position

                                    driveForward(0.5, -45);
                                    telemetry.addData("going to", gp);
                                    telemetry.update();
                                    tfod.deactivate();

                                    strafeLeft(0.5,25);
                                    strafeLeft(0.5,-25);

                                    driveForward(0.5,-65);
                                    //driveWithSensor(-0.5, 15);
                                    telemetry.addData("distance", rDS.getDistance(DistanceUnit.INCH));
                                    telemetry.update();
                                    turnLeft(0.5, 22.5);
                                    strafeLeft(0.5,10);
                                    driveForward(0.5,-75);
                                    flipUp.setPosition(0.4);
                                    extendUp.setTargetPosition(3500);
                                    extendUp.setPower(0.4);
                                    sleep(2000);
                                    flipUp.setPosition(0);
                                    sleep(100);
                                    flipUp.setPosition(0);
                                    extendUp.setTargetPosition(10);
                                    extendUp.setPower(0.4);


                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    gp = "right";
                                    strafeLeft(0.5,40);
                                    //sleep(4000); //allow the thing to read the gold position

                                    driveForward(0.5, 22.5);
                                    telemetry.addData("going to", gp);
                                    telemetry.update();
                                    tfod.deactivate();

                                    strafeLeft(0.5,25);
                                    strafeLeft(0.5,-25);

                                    driveForward(0.5,-165);
                                    //driveWithSensor(-0.5, 15);
                                    telemetry.addData("distance", rDS.getDistance(DistanceUnit.INCH));
                                    telemetry.update();
                                    turnLeft(0.5, 22.5);

                                    strafeLeft(0.5,10);
                                    driveForward(0.5,-75);
                                    flipUp.setPosition(0);
                                    extendUp.setTargetPosition(1000);
                                    extendUp.setPower(0.4);
                                    flipUp.setPosition(0.92);
                                    sleep(100);
                                    flipUp.setPosition(0);
                                    extendUp.setTargetPosition(10);
                                    extendUp.setPower(0.4);

                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    gp = "center";
                                    strafeLeft(0.5,40);
                                    //sleep(4000); //allow the thing to read the gold position
                                    driveForward(0.5, -15);
                                    telemetry.addData("going to", gp);
                                    telemetry.update();
                                    tfod.deactivate();

                                    strafeLeft(0.5,25);
                                    strafeLeft(0.5,-25);

                                    driveForward(0.5,-95);
                                    //driveWithSensor(-0.5, 15);
                                    telemetry.addData("distance", rDS.getDistance(DistanceUnit.INCH));
                                    telemetry.update();
                                    turnLeft(0.5, 22.5);
                                    strafeLeft(0.5,10);
                                    driveForward(0.5,-75);
                                    flipUp.setPosition(0);
                                    extendUp.setTargetPosition(1000);
                                    extendUp.setPower(0.4);
                                    flipUp.setPosition(0.92);
                                    sleep(100);
                                    flipUp.setPosition(0);
                                    extendUp.setTargetPosition(10);
                                    extendUp.setPower(0.4);
                                }
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }




    private void dropLift(int position, double power){

        liftArm.setTargetPosition(position);
        liftArm.setPower(power);
        //Added by AK
        liftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
}

