package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="AK_TeleOp")
@Disabled
public class AK_TeleOp extends AKRobot4592 {

    @Override
    public void runOpMode() throws InterruptedException{
        setup();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            /*
             * Gamepad 1
             *
             * Gamepad 1 controls the motors via the left stick, and it controls the
             * wrist/claw via the a,b, x, y buttons
             */


            // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
            // 1 is full down
            // direction: left_stick_x ranges from -1 to 1, where -1 is full left
            // and 1 is full right
            float movex = gamepad1.left_stick_y;
            float movey = gamepad1.left_stick_x;


            // roll: right_stick_x ranges from -1 to 1, where -1 is full counterclockwise, and
            // 1 is full clockwise

            float roll = gamepad1.left_trigger;

            // clip the right/left values so that the values never exceed +/- 1
            movex = Range.clip(movex, -1, 1);
            movey = Range.clip(movey, -1, 1);
            roll = Range.clip((roll*2), -1, 1);

            // scale the joystick value to make it easier to control
            // the robot more precisely at slower speeds.
            movex = (float)scaleInput(movex);
            movey = (float)scaleInput(movey);
            roll = (float)scaleInput(roll);

            // write the values to the motors
            leftFront.setPower(Range.clip(-movex+movey+roll, -1, 1));
            leftRear.setPower(Range.clip(movex-movey+roll, -1, 1));
            rightFront.setPower(Range.clip(-movex-movey-roll, -1, 1));
            rightRear.setPower(Range.clip(movex+movey-roll, -1, 1));



        }

    }





}

