package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;

@TeleOp(name = " Test LiftArm", group="Test")
public class AK_TestLiftArm extends LinearOpMode {


    private DcMotor liftArm;
    private CRServo Intake;
    private int liftArmHomePosition = 0;
    private int liftArmTargetPosition = -3100;


    public void initLiftArm() {

        liftArm = hardwareMap.dcMotor.get("liftArm");
        liftArm.setDirection(DcMotor.Direction.REVERSE);
        //liftArm.setMode(STOP_AND_RESET_ENCODER);
        liftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

// Intake
        Intake = hardwareMap.crservo.get("Intake");
    }

    @Override
    public void runOpMode() {
        initLiftArm();
        waitForStart();
        while (opModeIsActive()) {

            if (gamepad2.b) {
                if (liftArm.getCurrentPosition() >= -1000) {
                    liftArm.setTargetPosition(liftArmTargetPosition);
                    liftArm.setPower(0.5);
                } else if (liftArm.getCurrentPosition() <= -1000) {
                liftArm.setTargetPosition(liftArmHomePosition);
                liftArm.setPower(1.0);
                }
            }

            /*if (gamepad2.a) {
                Intake.setPower(0.8);
            }
*/

            Intake.setPower(-1*((gamepad2.left_stick_y)*0.85));




            telemetry.addData("position", liftArm.getCurrentPosition());
            telemetry.update();
        }
    }
}
