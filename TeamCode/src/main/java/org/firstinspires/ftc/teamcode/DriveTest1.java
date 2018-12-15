package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotBase;

import java.util.concurrent.TimeUnit;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;


@TeleOp(name = "DriveTest1")

public class DriveTest1 extends RobotBase {



    @Override
    public void runOpMode() throws InterruptedException {
        setup();
        // Initialize glyph arm


        waitForStart();



        left_f.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_f.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (opModeIsActive()) {
            // update


            final int drop_position = 3000;
            final int original_position = 0;

            //strafe
            double leftFront_Power = -1.5 * Math.cos(gamepad1.left_stick_y) * Math.sin(gamepad1.left_stick_x);
            double rightFront_Power = -leftFront_Power;
            double leftRear_Power = -leftFront_Power;
            double rightRear_Power = leftFront_Power;

            //drive
            double frontRight = -gamepad1.right_stick_y;
            double rearRight = -gamepad1.right_stick_y;
            double frontLeft = gamepad1.left_stick_y;
            double rearLeft = gamepad1.left_stick_y;


            if (gamepad2.x) {

                climb(); //when x is pressed, the lift arm will go down

            } else if (gamepad2.b) {

                drop(); //when b is pressed, the lift arm will go up
            }



           /* while(gamepad2.right_bumper){

                extend();

            }

            while(gamepad2.left_bumper){

                retract();

            }

            double inPower = gamepad2.left_stick_y;
            intake(inPower);
*/


            telemetry.addData("leftfrontpower: ", leftFront_Power);
            telemetry.addData("leftrearpower: ", leftRear_Power);
            telemetry.addData("rightfrontpower: ", rightFront_Power);
            telemetry.addData("rightrearpower: ", rightRear_Power);
            telemetry.addData("lift position", lift_arm.getCurrentPosition());
            /*
            if (isGold) {
                telemetry.addLine("IT WORKS BABYŸŸŸŸŸŸ;;;;;;;;;;;;YY");
            } else {
                telemetry.addLine("THIS AIN'T IT");
            }
*/
            drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);
            drive(-frontLeft, -frontRight, -rearLeft, -rearRight);
            telemetry.update();
            idle();


            telemetry.update();
            idle();
        }

    }


    private void drive( double lf, double rf, double lr, double rr1){

        left_f.setPower(lf);
        right_f.setPower(rf);
        left_r.setPower(lr);
        right_r.setPower(rr1);

    }

    private void drop() throws InterruptedException{

        lift_arm.setDirection(DcMotorSimple.Direction.FORWARD);
        lift_arm.setPower(-0.7);
        TimeUnit.MILLISECONDS.sleep(100);
    }

    private void climb() throws InterruptedException{

        lift_arm.setDirection(DcMotorSimple.Direction.REVERSE);
        lift_arm.setPower(0.7);
        TimeUnit.MILLISECONDS.sleep(100);
    }

/*   private void extend(){

        e_l.setPower(0.7);
        e_r.setPower(0.7);

    }

    private void retract(){

        e_l.setPower(-0.7);
        e_r.setPower(-0.7);

    }

    private void intake(inP){

        left_in.setPower(inP);
        right_in.setPower(inP);

    }

*/


}

