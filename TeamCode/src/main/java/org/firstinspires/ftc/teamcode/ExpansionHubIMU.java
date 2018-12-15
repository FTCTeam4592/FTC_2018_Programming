package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


import java.util.Locale;

/**
 * BNO055 Inertial Motion Unit (IMU), internal to the Rev Expansion Hub
 * Created by Benjamin Ward on 12/17/17.
 */
public class ExpansionHubIMU {
    private BNO055IMU imu; // The IMU sensor object

    private Orientation angles;
    private double headingOffset;

    public ExpansionHubIMU(HardwareMap hardwareMap) {
        // Retrieve the IMU. We expect the IMU to be named "imu", as is default.
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }

    /**
     * Initialize the IMU. This requires a HardwareMap to access the internal IMU.
     */
    public void initialize() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // See the calibration opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here literally does nothing.
        parameters.accelerationIntegrationAlgorithm = new NoneAccelerationIntegrator();

        // Initialize the IMU with specified parameters.
        imu.initialize(parameters);

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    public void update(Telemetry telemetry) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        // gravity = imu.getGravity();

        double heading = angles.firstAngle,
                roll = angles.secondAngle,
                pitch = angles.thirdAngle;

        if (telemetry != null)
            telemetry.addData("Heading", formatAngle(AngleUnit.DEGREES, heading));

        // Magnetometer data:
        // String.format(Locale.getDefault(), "%.3f",
        // Math.sqrt(gravity.xAccel * gravity.xAccel
        //      + gravity.yAccel * gravity.yAccel
        //      + gravity.zAccel * gravity.zAccel));

//        String system_status = imu.getSystemStatus().toShortString();
//        String calibration_status = imu.getCalibrationStatus().toString();
    }


    public void zero() {
        headingOffset = angles.firstAngle;
        update(null);
    }

    public double getHeading() {
        return (angles.firstAngle - headingOffset) % 360;
    }


    private String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    private String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    /**
     * {@link NoneAccelerationIntegrator} is an integrator that doesn't actually do anything.
     */
    public class NoneAccelerationIntegrator implements BNO055IMU.AccelerationIntegrator {
        BNO055IMU.Parameters parameters;
        Acceleration acceleration;

        @Override
        public void initialize(BNO055IMU.Parameters parameters, Position initialPosition, Velocity initialVelocity) {
            this.parameters = parameters;
        }

        @Override
        public Position getPosition() {
            return new Position();
        }

        @Override
        public Velocity getVelocity() {
            return new Velocity();
        }

        @Override
        public Acceleration getAcceleration() {
            return this.acceleration == null ? new Acceleration() : this.acceleration;
        }

        /**
         * Do nothing, and just store the data.
         */
        @Override
        public void update(Acceleration linearAcceleration) {
            // We should always be given a timestamp here
            if (linearAcceleration.acquisitionTime != 0) {
                acceleration = linearAcceleration;
            }
        }
    }
}
